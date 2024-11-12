package woongjin.gatherMind.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import woongjin.gatherMind.DTO.QuestionDTO;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findTop3ByMember_MemberIdOrderByCreatedAtDesc(String memberId);


    @Query("SELECT q.study.title FROM Question q WHERE q.id = :questionId")
    Optional<String> findStudyTitleByQuestionId(@Param("questionId") Long questionId);

    // QuestionRepository에 맞춤형 쿼리 메서드 추가
    @Query("SELECT new woongjin.gatherMind.DTO.QuestionDTO(q.id, q.content, q.title, m.memberId, s.title) " +
            "FROM Question q JOIN q.studyMember sm JOIN sm.member m JOIN sm.study s " +
            "WHERE q.id = :questionId")
    Optional<QuestionDTO> findQuestionDTOById(@Param("questionId") Long questionId);
}