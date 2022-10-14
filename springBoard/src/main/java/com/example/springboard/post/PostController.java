package com.example.springboard.post;

import com.example.springboard.answer.AnswerForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;

import java.util.List;

@RequestMapping("/post")
@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;

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
        model.addAttribute("question", post);
        return "question_detail";
    }

    @GetMapping("/create")
    public String postCreate(PostForm postForm) {
        return "question_form";
    }


    @PostMapping("/create")
    public String postCreate(@Valid PostForm postForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        this.postService.create(postForm.getSubject(), postForm.getContent());
        return "redirect:/post/list";
    }

    @GetMapping("/createTest")
    public String createTest() {
        this.postService.createTest();
        return "redirect:/post/list";
    }
}
