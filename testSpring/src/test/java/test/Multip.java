package test;

import d0.testSpring.Question;
import org.junit.Test;

import static org.junit.Assert.*;
public class Multip {

    @Test
    public void multiply() {
        Question q1 = new Question();
        Question q2 = q1;
        assertEquals(q1, q2);
    }
}
