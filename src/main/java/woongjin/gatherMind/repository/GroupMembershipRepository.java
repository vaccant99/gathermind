package woongjin.gatherMind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import woongjin.gatherMind.entity.GroupMembership;

public interface GroupMembershipRepository extends JpaRepository<GroupMembership, Long> {
}
