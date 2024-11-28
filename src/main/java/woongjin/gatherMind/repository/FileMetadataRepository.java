package woongjin.gatherMind.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import woongjin.gatherMind.DTO.FileMetadataUrlDTO;
import woongjin.gatherMind.entity.FileMetadata;

import java.util.Optional;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {

    Optional<FileMetadata> findByShortUrlKey(String shortUrlKey);

    FileMetadataUrlDTO findByEntityFileMapping_Question_QuestionId(Long questionId);

    boolean existsByShortUrlKey(String shortUrlKey);
}
