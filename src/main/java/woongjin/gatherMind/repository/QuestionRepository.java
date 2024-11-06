package woongjin.gatherMind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import woongjin.gatherMind.DTO.QuestionDTO;
import woongjin.gatherMind.entity.Question;


import java.util.List;

public interface QuestionRepository  extends JpaRepository<Question, Long> {

    List<QuestionDTO> findByStudyMember_Study_StudyIdOrderByCreatedAtDesc(Long studyId);
}
