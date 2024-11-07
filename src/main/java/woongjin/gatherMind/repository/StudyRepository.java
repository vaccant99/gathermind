package woongjin.gatherMind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import woongjin.gatherMind.DTO.MemberAndStatusRoleDTO;
import woongjin.gatherMind.entity.Study;

import java.util.List;
import java.util.Optional;

public interface StudyRepository extends JpaRepository<Study, Long> {

    Optional<Study> findById(Long studyId);

    @Query( "SELECT new woongjin.gatherMind.DTO.MemberAndStatusRoleDTO(m.memberId, m.nickname, sm.role, sm.status) " +
            "FROM Member m "+
            "JOIN m.studyMembers sm "+
            "WHERE sm.study.studyId = :studyId")
    List<MemberAndStatusRoleDTO> findMemberByStudyId(@Param("studyId") Long studyId);
}
