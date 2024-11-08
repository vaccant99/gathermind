package woongjin.gatherMind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import woongjin.gatherMind.entity.StudyMember;

import java.util.Optional;

@Repository
public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {

    @Query("SELECT sm FROM StudyMember sm WHERE sm.member.memberId = :memberId AND sm.study.studyId = :studyId")
    Optional<StudyMember> findByMemberIdAndStudyId(@Param("memberId") String memberId, @Param("studyId") Long studyId);}
