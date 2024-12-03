package woongjin.gatherMind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import woongjin.gatherMind.DTO.MemberAndStatusRoleDTO;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.enums.MemberStatus;

import java.util.List;
import java.util.Optional;

public interface StudyRepository extends JpaRepository<Study, Long> {

//    Optional<Study> findById(Long studyId);

    List<Study> findAllByStudyIdIn(List<Long> studyIds);

    @Query( "SELECT new woongjin.gatherMind.DTO.MemberAndStatusRoleDTO(m.memberId, m.nickname, sm.role, sm.status, sm.studyMemberId) " +
            "FROM Member m "+
            "JOIN m.studyMembers sm "+
            "WHERE sm.study.studyId = :studyId")
    List<MemberAndStatusRoleDTO> findMemberByStudyId(@Param("studyId") Long studyId);

    Optional<Study> findByTitle(String title);

    List<Study> findByStudyMembers_Member_MemberId(String memberId);

    List<Study> findByStudyMembers_Member_MemberIdAndStudyMembers_Status(String memberId, MemberStatus memberStatus);

}
