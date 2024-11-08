package woongjin.gatherMind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import woongjin.gatherMind.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
