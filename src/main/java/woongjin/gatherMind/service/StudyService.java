package woongjin.gatherMind.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.dto.StudyDto;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.repository.StudyMemberRepository;
import woongjin.gatherMind.repository.StudyRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class StudyService {

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyMemberRepository studyMemberRepository;


    public Study createStudy(StudyDto studyDto) {
        Study study = new Study();
        study.setTitle(studyDto.getTitle());
        study.setDescription(studyDto.getDescription());
        study.setStatus(studyDto.isStatus());

        return studyRepository.save(study);


    }

    public StudyDto getStudy(Long studyId) {

        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new RuntimeException("Study not found"));


        return new StudyDto(
                study.getStudyId(),
                study.getTitle(),
                study.getDescription(),

                study.isStatus(),
                study.getCreatedAt()
        );
    }


    public List<StudyDto> getAllStudies() {
        List<Study> studies = studyRepository.findAll();


        return studies.stream()
                .map(study -> new StudyDto(
                        study.getStudyId(),
                        study.getTitle(),
                        study.getDescription(),
                        study.isStatus(),
                        study.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }


    public List<StudyDto> getStudiesbyMemberId(String memberId) {


        List<Long> studyIds = studyMemberRepository.findStudyIdsByMemberId(memberId);

        if (studyIds.isEmpty()) {

            throw new NoSuchElementException("No studies found for the member with ID " + memberId);
        }

        List<Study> studies = studyRepository.findAllByStudyIdIn(studyIds);

        return studies.stream()
                .map(study -> new StudyDto(
                        study.getStudyId(),
                        study.getTitle(),
                        study.getDescription(),
                        study.isStatus(),
                        study.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
}