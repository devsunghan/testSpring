package com.example.springboard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.springboard.answer.Answer;
import com.example.springboard.answer.AnswerRepository;
import com.example.springboard.answer.AnswerService;
import com.example.springboard.post.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class BoardTest {


    @Autowired
    private AnswerService answerService;

    @Autowired
    private PostService postService;



    @Test
    void testJpa() {
        for (int i = 1; i <= 300; i++) {
            String subject = String.format("test data:[%03d]", i);
            String content = "test";
            this.postService.create(subject, content, null);
        }
    }
}
