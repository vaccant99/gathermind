package woongjin.gatherMind.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import woongjin.gatherMind.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q WHERE q.memberId = :memberId ORDER BY q.createdAt DESC")
    List<Question> findRecentQuestionsByMemberId(@Param("memberId") String memberId);

    List<Question> findTop3ByMemberIdOrderByCreatedAtDesc(String memberId);
}
