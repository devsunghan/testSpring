package com.example.springboard.post;

import com.example.springboard.answer.AnswerForm;
import com.example.springboard.user.SiteUser;
import com.example.springboard.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;


// @RequiredArgsConstructorアノテーションは"final", "@NotNull"がついているフィールドのConstructorを注入するアノテーションです。
// ここでは"Service"オブジェクトにconstructorを注入します。
@RequestMapping("/post")
@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;
    private final UserService userService;

//    @RequestParamはURLに接続した時、変数を指定し、パラメータを入れることができます。pageの場合、defaultは0にし、ページのボタンを押すたびに変わるようにしました。
//    postServiceからページの情報をもらい、modelに追加した後、ページングしたhtmlをreturnします。
    @RequestMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<Post> paging = this.postService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }

//    "http://localhost:8080/post"に接続すると、listメソッドが機能するようにredirectしました。
    @RequestMapping("/")
    public String root() {
        return "redirect:/post/list";
    }

//    @PathVariableは{}で囲んだURLを変数に入れるアノテーションです。
    @RequestMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Post post = this.postService.getPost(id);
        model.addAttribute("post", post);
        return "question_detail";
    }

//    @PreAuthorize Annotationはログインが必要なメソッドであることを意味します。
//    GetMappingで"/create"に接続すると、投稿するためのフォームをreturnします。
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String postCreate(PostForm postForm) {
        return "question_form";
    }

//    GetMappingで貰ったフォームにデータを入れ、postMappingでsubmitすると、フォームにエラーがあるか確認します。エラーがあれば、フォームをreturnし、表示します。
//    エラーが無ければ、今ログインしているユーザーのIDをPrincipalオブジェクトからもらい、Userインスタンスを作ります。
//    その後、postServiceのcreateメソッドでポストをデータベースに入れます。終わったらlistのページをreturnします。

//    bindingResultはフォームにある制約条件が満たされているかを確認するオブジェクトです。
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String postCreate(@Valid PostForm postForm, BindingResult bindingResult,
                             Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.postService.create(postForm.getSubject(), postForm.getContent(), siteUser);
        return "redirect:/post/list";
    }

//    aws環境で実行すると、awsの中に新しいデータベースが作られます。
//    そこにページングようのデータを入れるために作ったメソッドです。
    @GetMapping("/createTest")
    public String createTest() {
        this.postService.createTest();
        return "redirect:/post/list";
    }

//    ポストを修正するメソッドです。修正したいポストを投稿したユーザーを探し、今ログインしているユーザーと同じユーザーか確認します。
//    同じユーザーではない場合、"修正の権限がありません。"というエラーを起こします。
//    同じユーザーの場合、修正前の内容を入れたフォームをreturnします。
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String postModify(PostForm postForm, @PathVariable("id") Integer id, Principal principal) {
        Post post = this.postService.getPost(id);
        if (!post.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "修正の権限がありません。");
        }
        postForm.setSubject(post.getSubject());
        postForm.setContent(post.getContent());
        return "question_form";
    }

//    エラーがあればエラーを表示するフォームをreturn、無ければポスタを投稿したユーザーと現在ログインしているユーザーを比較します。
//    違うユーザーならエラーページを表示、同じユーザーなら修正したタイトルと内容でupdateします。
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String postModify(@Valid PostForm postForm, BindingResult bindingResult,
                             @PathVariable("id") Integer id, Principal principal) {

        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Post post = this.postService.getPost(id);
        if (!post.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "修正の権限がありません。");
        }
        this.postService.modify(post, postForm.getSubject(), postForm.getContent());
        return String.format("redirect:/post/detail/%s", id);
    }

//    ポストを削除するメソッドです。ポストを投稿したユーザーとログインしているユーザーを比較し、違うとエラーページを、
//    同じならポストをデータベースから削除します。
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String postDelete (Principal principal, @PathVariable("id") Integer id) {
        Post post = this.postService.getPost(id);
        if (!post.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "削除の権限がありません。");
        }
        this.postService.delete(post);
        return "redirect:/";
    }
}
