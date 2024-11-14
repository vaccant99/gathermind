package woongjin.gatherMind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import woongjin.gatherMind.entity.Study;

import java.util.List;
import java.util.Optional;


@Repository
public interface StudyRepository extends JpaRepository<woongjin.gatherMind.entity.Study, Long> {

    Optional<woongjin.gatherMind.entity.Study> findById(Long study_id);

    List<Study> findAllByStudyIdIn(List<Long> studyIds);



}
