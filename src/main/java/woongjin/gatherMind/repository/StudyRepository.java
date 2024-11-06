package woongjin.gatherMind.repository;

import woongjin.gatherMind.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
