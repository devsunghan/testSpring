package test;

import d0.testSpring.Question;
import d0.testSpring.QuestionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.time.LocalDateTime;


public class ApplicationTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    public void testJpa() {
        Question q1 = new Question();
        q1.setSubject("asdasd");
        q1.setContent("asdasdff");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1); // ポストセーブ
    }
}
