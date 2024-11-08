package woongjin.gatherMind.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import woongjin.gatherMind.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query("SELECT a FROM Answer a WHERE a.memberId = :memberId ORDER BY a.createdAt DESC")
    List<Answer> findRecentAnswersByMemberId(@Param("memberId") String memberId);

    List<Answer> findTop3ByMemberIdOrderByCreatedAtDesc(String memberId);
}
