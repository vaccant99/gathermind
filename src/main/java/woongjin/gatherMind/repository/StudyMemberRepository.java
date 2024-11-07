package woongjin.gatherMind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import woongjin.gatherMind.entity.StudyMember;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {
}
