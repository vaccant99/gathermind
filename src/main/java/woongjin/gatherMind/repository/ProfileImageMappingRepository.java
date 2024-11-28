package woongjin.gatherMind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.entity.ProfileImageMapping;

import java.util.Optional;

@Repository
public interface ProfileImageMappingRepository extends JpaRepository<ProfileImageMapping, Long> {
    Optional<ProfileImageMapping> findByMember(Member member);
}
