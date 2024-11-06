package woongjin.gatherMind.service;

import woongjin.gatherMind.DTO.StudyDTO;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.repository.StudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class StudyService {
    @Autowired
    private StudyRepository studyRepository;

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


}
