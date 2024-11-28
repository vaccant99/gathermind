package woongjin.gatherMind.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import woongjin.gatherMind.DTO.StudyDTO;
import woongjin.gatherMind.constants.StatusConstants;
import woongjin.gatherMind.entity.StudyMember;

import java.util.List;
import java.util.Optional;


@Repository
public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {


    @Query("SELECT sm FROM StudyMember sm WHERE sm.member.memberId = :memberId AND sm.study.studyId = :studyId")
    Optional<StudyMember> findByMemberIdAndStudyId(@Param("memberId") String memberId, @Param("studyId") Long studyId);

    Optional<StudyMember> findByMember_MemberIdAndStudy_StudyId(String memberId, Long studyId);

    List<StudyMember> findByMember_MemberId(String memberId);

    @Query("SELECT sm.study.studyId FROM StudyMember sm WHERE sm.member.memberId = :memberId")
    List<Long> findStudyIdsByMemberId(@Param("memberId") String memberId);

    @Query("SELECT COUNT(sm) FROM StudyMember sm WHERE sm.member.memberId = :memberId")
    long countByMemberId(@Param("memberId") String memberId);

    @Query("SELECT sm.study.studyId FROM StudyMember sm WHERE sm.member.memberId = :memberId AND sm.status = upper(:status)")
    List<Long> findStudyIdsByMemberIdAndStatus(@Param("memberId") String memberId, @Param("status") String status);

    boolean existsByMember_MemberIdAndStudy_StudyId(String memberId, Long studyId);

    long countByStudy_StudyIdAndStatus(Long studyId, String status);
}


