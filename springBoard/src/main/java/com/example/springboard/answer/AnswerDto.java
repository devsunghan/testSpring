package com.example.springboard.answer;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto {
    private Integer id;

    private String content;

    private LocalDateTime createDate;


}
