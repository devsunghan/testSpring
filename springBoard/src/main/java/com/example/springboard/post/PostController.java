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

@RequestMapping("/post")
@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @RequestMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<Post> paging = this.postService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }

    @RequestMapping("/")
    public String root() {
        return "redirect:/post/list";
    }

    @RequestMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Post post = this.postService.getPost(id);
        model.addAttribute("post", post);
        return "question_detail";
    }

//    @PreAuthorize Annotationはログインが必要なメソッドであることを意味します。
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String postCreate(PostForm postForm) {
        return "question_form";
    }

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

    @GetMapping("/createTest")
    public String createTest() {
        this.postService.createTest();
        return "redirect:/post/list";
    }

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
