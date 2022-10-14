package d0.testSpring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping("go")
    public String test() {
        return "test";
    }

    @GetMapping("ques")
    public String jspTest(Model model) {
        Db db = new Db();
        EntityManagerFactory emf = db.create();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Member member = em.find(Member.class, 1L);


        String s = member.getName();
        model.addAttribute("test", s);
        return "test";
    }

    @GetMapping("db")
    public String DbTest() {

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
//            System.out.println(e);
//            System.out.println("에러 일어남");

//        }finally {
//            em.close();
//
//        }
//        emf.close();

        return "test";
    }

}
