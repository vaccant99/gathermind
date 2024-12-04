package woongjin.gatherMind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import woongjin.gatherMind.entity.EntityFileMapping;

import java.util.List;

public interface EntityFileMappingRepository extends JpaRepository<EntityFileMapping, Long> {

    List<EntityFileMapping> findByQuestion_QuestionId(Long questionId);
    EntityFileMapping findByFileMetadata_FileMetadataId(Long fileMetadataId);
}
