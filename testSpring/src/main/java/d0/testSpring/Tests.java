package d0.testSpring;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class Tests {

    public static void main(String[] args) {


        Question q1 = new Question();
        q1.setSubject("test");
        q1.setContent("how to do?");
        q1.setCreateDate(LocalDateTime.now());
        Tests t1 = new Tests();
    }

//    public static void createQuestion() {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sa");
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//
//        try {
//            tx.begin();
//            Member member = new Member();
//            member.setId(7L);
//            member.setName("ㅇㅁㄴddㅇ");
//            em.persist(member);
//            tx.commit();
//
//        }catch (Exception e) {
//            tx.rollback();
//        }finally {
//            em.close();
//
//        }
//        emf.close();
//
//
//    }
}
