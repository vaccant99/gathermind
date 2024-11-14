package woongjin.gatherMind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.entity.StudyMember;

import java.util.List;


@Repository
public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {




    @Query("SELECT sm.study.studyId FROM StudyMember sm WHERE sm.member.memberId = :memberId")
    List<Long> findStudyIdsByMemberId(@Param("memberId") String memberId);






}
