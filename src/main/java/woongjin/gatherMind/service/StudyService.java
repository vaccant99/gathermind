package woongjin.gatherMind.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woongjin.gatherMind.DTO.*;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.entity.StudyMember;
import woongjin.gatherMind.exception.member.MemberNotFoundException;
import woongjin.gatherMind.exception.study.StudyNotFoundException;
import woongjin.gatherMind.repository.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;
    private final QuestionRepository questionRepository;
    private final ScheduleRepository scheduleRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final MemberRepository memberRepository;

    // 스터디 생성 (메서드 내에서 예외가 발생하면 자동으로 rollback)
    @Transactional
    public Study createStudy(StudyCreateRequestDTO dto) {

        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(
                        () ->  new MemberNotFoundException("Member with ID " + dto.getMemberId() + " not found"));

        // Study 엔티티 생성 및 저장
        Study study = toStudyEntity(dto);
        Study savedStudy = studyRepository.save(study);

        // StudyMember 엔티티 생성 및 저장
        StudyMember studyMember = createLeaderMember(savedStudy, member);
        studyMemberRepository.save(studyMember);

        return savedStudy;
    }

    // 스터디 생성
    public StudyDTO getStudyById(Long studyId) {
        Study study = studyRepository.findById(studyId).orElseThrow(() -> new StudyNotFoundException("study not found"));

        return StudyDTO.builder()
                        .title(study.getTitle())
                .description(study.getDescription())
                .status(study.getStatus())
                .build();
    }

    // 그룹 정보, 멤버 조회, 게시판 조회
    public StudyWithMembersDTO getStudyInfoWithMembers(Long studyId) {

        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new StudyNotFoundException("study not found"));

        Pageable pageable = PageRequest.of(0, 5);
        Page<QuestionDTO> result = questionRepository
                .findByStudyMember_Study_StudyIdOrderByCreatedAtDesc(studyId, pageable);

        return new StudyWithMembersDTO
                (study.getStudyId(),
                study.getTitle(),
                study.getDescription(),
                findMembersByStudyId(studyId),
                        result
        );
    }

    // 멤버 랭킹, 멤버 조회
    public MemberAndBoardDTO getMembersAndBoard(Long studyId) {

        Pageable pageable = PageRequest.of(0, 5);
        Page<QuestionDTO> result = questionRepository
                .findByStudyMember_Study_StudyIdOrderByCreatedAtDesc(studyId, pageable);

        return new MemberAndBoardDTO(
                findMembersByStudyId(studyId),
                result
        );
    }

    // 스터디 일정 조회
    public List<ScheduleDTO> getScheduleByStudyId(Long studyId) {
        return scheduleRepository.findByStudy_StudyId(studyId);
    }

// 스터디 수정
    public Study updateStudy(Long id, Study studyData) {
        Study extistingStudy = studyRepository.findById(id).orElseThrow(() -> new StudyNotFoundException("study not found"));

        if(studyData.getTitle() != null) {
            extistingStudy.setTitle(studyData.getTitle());
        }
        if(studyData.getDescription() != null) {
            extistingStudy.setDescription(studyData.getDescription());
        }
        if(studyData.getStatus() != null) {
            extistingStudy.setStatus(studyData.getStatus());
        }

        return studyRepository.save(extistingStudy);
    }

    // 스터디 게시판 조회
    public Page<QuestionDTO> getBoards(Long studyId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<QuestionDTO> result = questionRepository
                .findByStudyMember_Study_StudyIdOrderByCreatedAtDesc(studyId, pageable);


        return new PageImpl<>(result.getContent(), pageable, result.getTotalElements());
    }

    private List<MemberAndStatusRoleDTO> findMembersByStudyId(Long studyId) {
        return studyRepository.findMemberByStudyId(studyId);
    }

    private List<QuestionDTO> findQuestionsByStudyId(Long studyId) {
        return questionRepository.findByStudyMember_Study_StudyIdOrderByCreatedAtDesc(studyId);
    }



    // Leader StudyMember 엔티티 생성 메서드
    private StudyMember createLeaderMember(Study study, Member member) {
        StudyMember studyMember = new StudyMember();
        studyMember.setRole("Leader");
        studyMember.setStatus("");
        studyMember.setProgress("");
        studyMember.setStudy(study);
        studyMember.setMember(member);
        return studyMember;
    }

    // Study 엔티티로 변환하는 메서드
    private Study toStudyEntity(StudyCreateRequestDTO dto) {
        Study study = new Study();
        study.setStatus(dto.getStatus());
        study.setTitle(dto.getTitle());
        study.setDescription(dto.getDescription());
        return study;
    }


}
