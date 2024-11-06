package woongjin.gatherMind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import woongjin.gatherMind.DTO.MemberAndStatusRoleDTO;
import woongjin.gatherMind.entity.Member;


import java.util.List;

public interface MemberRepository extends JpaRepository<Member, String> {

}
