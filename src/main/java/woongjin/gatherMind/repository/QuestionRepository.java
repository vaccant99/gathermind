package woongjin.gatherMind.repository;

import woongjin.gatherMind.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
