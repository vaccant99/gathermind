package woongjin.gatherMind.repository;

import woongjin.gatherMind.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {
    Optional<StudyMember> findByMember_MemberIdAndStudy_StudyId(String memberId, Long studyId);
    List<StudyMember> findByMember_MemberId(String memberId);
}