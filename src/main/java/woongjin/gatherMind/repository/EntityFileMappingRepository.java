package woongjin.gatherMind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import woongjin.gatherMind.entity.EntityFileMapping;

public interface EntityFileMappingRepository extends JpaRepository<EntityFileMapping, Long> {

    EntityFileMapping findByQuestion_QuestionId(Long questionId);
}
