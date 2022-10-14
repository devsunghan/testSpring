package d0.testSpring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public interface QuestionRepository extends JpaRepository<Question, Integer>{

}
