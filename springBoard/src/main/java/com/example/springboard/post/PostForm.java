package com.example.springboard.post;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PostForm {

    @NotEmpty(message="タイトルを入力してください。")
    @Size(max=100)
    private String subject;

    @NotEmpty(message="内容を入力してください。")
    private String content;
}
