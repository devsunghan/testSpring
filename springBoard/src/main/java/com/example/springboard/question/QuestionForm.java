package com.example.springboard.question;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class QuestionForm {

    @NotEmpty(message="タイトルを入力してください。")
    @Size(max=100)
    private String subject;

    @NotEmpty(message="内容を入力してください。")
    private String content;
}
