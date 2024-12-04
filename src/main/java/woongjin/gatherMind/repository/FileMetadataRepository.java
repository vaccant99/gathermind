package woongjin.gatherMind.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import woongjin.gatherMind.DTO.FileMetadataUrlDTO;
import woongjin.gatherMind.entity.FileMetadata;

import java.util.Optional;

@Repository
public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {

    Optional<FileMetadata> findByShortUrlKey(String shortUrlKey);

    Optional<FileMetadata> findByFileKey(String fileKey);

    FileMetadataUrlDTO findByEntityFileMapping_Question_QuestionId(Long questionId);

    @Query("SELECT new woongjin.gatherMind.DTO.FileMetadataUrlDTO(f.fileMetadataId, f.fileName, f.shortUrlKey, f.fileKey, f.fileSize, f.uploadByUserId) " +
            "FROM FileMetadata f " +
            "JOIN f.entityFileMapping efm " +
            "WHERE efm.question.questionId = :questionId " +
            "AND (f.fileKey NOT LIKE '%.jpg' " +
            "AND f.fileKey NOT LIKE '%.jpeg' " +
            "AND f.fileKey NOT LIKE '%.png' " +
            "AND f.fileKey NOT LIKE '%.gif')") // 이미지 확장자 제외 조건
    Optional<FileMetadataUrlDTO> findNonImageFilesByQuestionId(@Param("questionId") Long questionId);

    boolean existsByShortUrlKey(String shortUrlKey);
}
