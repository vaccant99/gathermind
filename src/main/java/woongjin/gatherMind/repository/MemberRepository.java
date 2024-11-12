package woongjin.gatherMind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import woongjin.gatherMind.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
}
