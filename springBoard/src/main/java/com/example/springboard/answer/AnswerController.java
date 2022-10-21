package com.example.springboard.answer;

import com.example.springboard.post.Post;
import com.example.springboard.post.PostService;


import com.example.springboard.user.SiteUser;
import com.example.springboard.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {


    private final PostService postService;
    private final AnswerService answerService;
    private final UserService userService;


//    PrincipalはSpringSecurityで提供するオブジェクトで
//    principal.getName()を使うと、現在ログインしたユーザーのIDを探すことができます。

//　　　コメントを付けると、現在ログインしているユーザーの情報をPrincipalから貰い、エラーが無ければ
//　　　DBにコメントを入れ、元のページに戻ります。
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm,
                               BindingResult bindingResult, Principal principal) {

        Post post = this.postService.getPost(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("post", post);
            return "question_detail";
        }
        this.answerService.create(post, answerForm.getContent(), siteUser);
        return String.format("redirect:/post/detail/%s", id);
    }

//    コメントを修正するメソッドです。まずログインしているかを判断し、ログイン状態ではないと、ログインページに移動します。
//    ログインしているとコメントを付けたユーザーと現在ログイン中のユーザーが違う場合、エラーページを
//    同じユーザーだったら修正前のコメント内容が入っているフォームをreturnします。
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String answerModify(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal) {
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "修正の権限がありません。");
        }
        answerForm.setContent(answer.getContent());
        return "answer_form";
    }

//    GetMappingで貰ったフォームをpostでsubmitすれば、フォームにエラーがあるか確認します。あればエラー表示をします。
//    その後、コメントを書いたユーザーとログイン中のユーザーを比較し、違うとエラーページを表示します。
//    同じユーザーなら、コメントを修正した後、元のページをreturnします。
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
                               @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "修正の権限がありません。");
        }
        this.answerService.modify(answer, answerForm.getContent());
        return String.format("redirect:/post/detail/%s", answer.getPost().getId());
    }

//    コメントを削除するメソッドです。ログイン中か確認し、コメントを書いたユーザーとログイン中のユーザーを比較します。
//    違うユーザーならエラーページを、同じユーザーならコメントを削除した後、元のページをreturnします。
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answerDelete(Principal principal, @PathVariable("id") Integer id) {
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "削除の権限がありません。");
        }
        this.answerService.delete(answer);
        return String.format("redirect:/post/detail/%s", answer.getPost().getId());
    }
}
