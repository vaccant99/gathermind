package woongjin.gatherMind.repository;

import woongjin.gatherMind.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyRepository extends JpaRepository<Study, Long> {
    Optional<Study> findByTitle(String title);
}