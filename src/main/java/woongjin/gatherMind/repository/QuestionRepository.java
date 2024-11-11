package woongjin.gatherMind.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import woongjin.gatherMind.DTO.QuestionWithoutAnswerDTO;
import woongjin.gatherMind.entity.Question;


import java.util.List;

public interface QuestionRepository  extends JpaRepository<Question, Long> {

    Page<QuestionWithoutAnswerDTO> findByStudyMember_Study_StudyIdOrderByCreatedAtDesc(Long studyId, Pageable pageable);
}
