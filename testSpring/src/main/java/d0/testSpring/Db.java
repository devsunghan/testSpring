package d0.testSpring;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Db {


    public EntityManagerFactory create() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sa");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        return emf;
//        try {
//            tx.begin();
//            Member member = new Member();
//            member.setId(15L);
//            member.setName("ㅇㅁㄴddㅇ");
//            em.persist(member);
//            tx.commit();
//
//        }catch (Exception e) {
//            tx.rollback();
//
//        }finally {
//            em.close();
//
//        }
//        emf.close();
    }
}
