package woongjin.gatherMind.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import woongjin.gatherMind.entity.FileMetadata;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
}
