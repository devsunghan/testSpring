package com.example.springboard.question;

import com.example.springboard.answer.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
    private Integer id;

    private String subject;

    private String content;

    private LocalDateTime createDate;

    private List<Answer> answerList;
}
