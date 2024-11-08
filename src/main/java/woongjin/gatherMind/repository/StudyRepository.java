package woongjin.gatherMind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import woongjin.gatherMind.entity.Study;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {
}
