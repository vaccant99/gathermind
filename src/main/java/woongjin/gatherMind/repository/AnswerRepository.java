package woongjin.gatherMind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import woongjin.gatherMind.DTO.AnswerDTO;
import woongjin.gatherMind.entity.Answer;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("SELECT new woongjin.gatherMind.DTO.AnswerDTO(a.answerId, a.content, a.createdAt, m.nickname) " +
            "FROM Answer a JOIN Member m ON m.memberId = a.memberId " +
            "WHERE a.question.questionId = :questionId")
    List<AnswerDTO> findAnswersByQuestionId(@Param("questionId") Long questionId);

    @Query("SELECT a FROM Answer a WHERE a.memberId = :memberId ORDER BY a.createdAt DESC")
    List<Answer> findRecentAnswersByMemberId(@Param("memberId") String memberId);

    List<Answer> findTop3ByMemberIdOrderByCreatedAtDesc(String memberId);

    List<Answer> findByMemberId(String memberId); // Member ID로 답변 목록 조회

    @Query("SELECT COUNT(a) FROM Answer a WHERE a.memberId = :memberId")
    long countByMemberId(@Param("memberId") String memberId);

}

