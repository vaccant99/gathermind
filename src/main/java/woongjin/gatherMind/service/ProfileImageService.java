package woongjin.gatherMind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import woongjin.gatherMind.config.JwtTokenProvider;
import woongjin.gatherMind.entity.FileMetadata;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.entity.ProfileImageMapping;
import woongjin.gatherMind.repository.FileMetadataRepository;
import woongjin.gatherMind.repository.MemberRepository;
import woongjin.gatherMind.repository.ProfileImageMappingRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ProfileImageService {

    private final Path uploadDir = Paths.get("uploads/profile-images");

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FileMetadataRepository fileMetadataRepository;

    @Autowired
    private ProfileImageMappingRepository profileImageMappingRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void updateProfileImage(MultipartFile file, String token) throws IOException {
        String memberId = jwtTokenProvider.extractMemberIdFromToken(token); // 토큰에서 멤버 ID 추출
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 파일 저장
        String fileName = saveFile(file);

        // 파일 메타데이터 저장
        FileMetadata fileMetadata = new FileMetadata(fileName, file.getContentType(), file.getSize());
        fileMetadataRepository.save(fileMetadata);

        // 프로필 이미지 매핑 업데이트
        ProfileImageMapping profileImageMapping = profileImageMappingRepository.findByMember(member)
                .orElse(new ProfileImageMapping());
        profileImageMapping.setFile(fileMetadata);
        profileImageMapping.setMember(member);
        profileImageMappingRepository.save(profileImageMapping);
    }

    public Resource getProfileImage(String token) throws IOException {
        String memberId = jwtTokenProvider.extractMemberIdFromToken(token); // 토큰에서 멤버 ID 추출
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        ProfileImageMapping mapping = profileImageMappingRepository.findByMember(member)
                .orElseThrow(() -> new IllegalArgumentException("프로필 이미지가 설정되지 않았습니다."));

        Path filePath = uploadDir.resolve(mapping.getFile().getFileName());
        if (!Files.exists(filePath)) {
            throw new IllegalArgumentException("프로필 이미지 파일이 존재하지 않습니다.");
        }

        return new UrlResource(filePath.toUri());
    }

    private String saveFile(MultipartFile file) throws IOException {
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadDir.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);
        return fileName;
    }
}
