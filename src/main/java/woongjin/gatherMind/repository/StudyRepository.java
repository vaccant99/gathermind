package woongjin.gatherMind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import woongjin.gatherMind.entity.Study;

import java.util.Optional;

public interface StudyRepository extends JpaRepository<Study, Long> {

    Optional<Study> findById(Long meetingId);
}
