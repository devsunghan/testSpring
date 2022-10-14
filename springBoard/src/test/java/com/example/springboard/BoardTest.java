package com.example.springboard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.springboard.answer.Answer;
import com.example.springboard.answer.AnswerRepository;
import com.example.springboard.answer.AnswerService;
import com.example.springboard.post.PostService;
import com.example.springboard.question.Question;
import com.example.springboard.question.QuestionRepository;
import com.example.springboard.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class BoardTest {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private PostService postService;

    @Test
    void testJpa1() {
        Question q1 = new Question();
        q1.setSubject("제목");
        q1.setContent("내용");
        q1.setCreateDate(LocalDateTime.now());
        this.questionService.save(q1);
    }

//    @Test
//    void testJpa2() {
//
//        Question q = this.questionRepository.findBySubjectAndContent("제목", "내용");
//        assertEquals(1, q.getId());
//    }
//
//    @Test
//    void testJpa3() {
//
//        List<Question> q = this.questionRepository.findBySubjectLike("제%");
//        assertEquals(1, q.get(0).getId());
//    }
//
//    @Test
//    void testJpa4() {
//
//        Optional<Question> oq = this.questionRepository.findById(1);
//        assertTrue(oq.isPresent());
//        Question q = oq.get();
//
//        Answer a = new Answer();
//        a.setContent("댓글");
//        a.setQuestion(q);
//        a.setCreateDate(LocalDateTime.now());
//        this.answerRepository.save(a);
//
//    }

    @Test
    void testJpa() {
        for (int i = 1; i <= 300; i++) {
            String subject = String.format("test data:[%03d]", i);
            String content = "test";
            this.postService.create(subject, content);
        }
    }
}
