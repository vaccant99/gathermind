package woongjin.gatherMind.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import woongjin.gatherMind.DTO.QuestionWithoutAnswerDTO;
import woongjin.gatherMind.entity.Question;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import woongjin.gatherMind.DTO.QuestionDTO;

import java.util.List;
import java.util.Optional;


public interface QuestionRepository  extends JpaRepository<Question, Long> {

    Page<QuestionWithoutAnswerDTO> findByStudyMember_Study_StudyIdOrderByCreatedAtDesc(Long studyId, Pageable pageable);

    List<Question> findTop3ByStudyMember_Member_MemberIdOrderByCreatedAtDesc(String memberId);

    // QuestionRepository에 맞춤형 쿼리 메서드 추가
    @Query("SELECT new woongjin.gatherMind.DTO.QuestionDTO(q.id, q.content, q.title, m.memberId, s.title) " +
            "FROM Question q JOIN q.studyMember sm JOIN sm.member m JOIN sm.study s " +
            "WHERE q.id = :questionId")
    Optional<QuestionDTO> findQuestionDTOById(@Param("questionId") Long questionId);

    @Query("SELECT COUNT(q) FROM Question q WHERE q.studyMember.member.memberId = :memberId")
    long countByMemberId(@Param("memberId") String memberId);
}

