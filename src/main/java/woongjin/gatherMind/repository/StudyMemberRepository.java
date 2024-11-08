package woongjin.gatherMind.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import woongjin.gatherMind.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {
    @Query("SELECT sm.study.title FROM StudyMember sm WHERE sm.member.memberId = :memberId")
    List<String> findStudyTitlesByMemberId(@Param("memberId") String memberId);

    List<StudyMember> findByMemberId(String memberId);
}
