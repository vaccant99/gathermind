package woongjin.gatherMind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.DTO.StudyDTO;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.repository.StudyMemberRepository;
import woongjin.gatherMind.repository.StudyRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudyService {

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyMemberRepository studyMemberRepository;

    public Optional<StudyDTO> findStudyById(Long studyId) {
        return studyRepository.findById(studyId)
                .map(study -> new StudyDTO(study.getTitle(), study.getDescription()));
    }

    public Study createStudy(StudyDTO studyDto) {
        Study study = new Study();
        study.setTitle(studyDto.getTitle());
        study.setDescription(studyDto.getDescription());
        study.setCreatedAt(LocalDateTime.now());
        study.setStatus(studyDto.getStatus());
        return studyRepository.save(study);
    }

    public Optional<Study> getStudyById(Long studyId) {
        return studyRepository.findById(studyId);
    }

    public Study updateStudy(Long studyId, StudyDTO studyDto) {
        return studyRepository.findById(studyId).map(study -> {
            study.setTitle(studyDto.getTitle());
            study.setDescription(studyDto.getDescription());
            study.setStatus(studyDto.getStatus());
            return studyRepository.save(study);
        }).orElseThrow(() -> new RuntimeException("Study not found"));
    }

    public StudyDTO convertToDTO(Study study) {
        StudyDTO dto = new StudyDTO();
        dto.setStudyId(study.getStudyId());
        dto.setTitle(study.getTitle());
        dto.setDescription(study.getDescription());
        dto.setCreatedAt(study.getCreatedAt());
        dto.setStatus(study.getStatus());
        return dto;
    }

    public List<String> getAllStudyTitles() {
        return studyRepository.findAll()
                .stream()
                .map(study -> study.getTitle())
                .collect(Collectors.toList());
    }
}