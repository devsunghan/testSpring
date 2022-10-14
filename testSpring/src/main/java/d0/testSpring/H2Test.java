package d0.testSpring;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;


public class H2Test {
    public static void main(String[] args) {

        System.out.println("asdasdasd");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sa");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();


        try {
            LocalDateTime local = LocalDateTime.now();
            System.out.println(local);
            tx.begin();

            Question q1 = new Question();

            q1.setSubject("hellooo!!!");
            q1.setContent("asdsafsafadasfafasdfsaasdsa");
            q1.setCreateDate(local);
            em.persist(q1);
            tx.commit();

        }catch (Exception e) {
            tx.rollback();
            System.out.println(e);
            System.out.println("에러 일어남");

        }finally {
            em.close();

        }
        emf.close();

    }
}
