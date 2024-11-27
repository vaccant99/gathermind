package woongjin.gatherMind.service;

import jakarta.servlet.UnavailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woongjin.gatherMind.DTO.*;
import woongjin.gatherMind.constants.RoleConstants;
import woongjin.gatherMind.constants.StatusConstants;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.entity.StudyMember;
import woongjin.gatherMind.exception.study.StudyNotFoundException;
import woongjin.gatherMind.exception.studyMember.StudyMemberNotFoundException;
import woongjin.gatherMind.repository.*;
import woongjin.gatherMind.DTO.StudyDTO;
import woongjin.gatherMind.repository.StudyMemberRepository;
import woongjin.gatherMind.repository.StudyRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static woongjin.gatherMind.util.StudyMemberUtils.checkAdminRole;


@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;
    private final QuestionRepository questionRepository;
    private final ScheduleRepository scheduleRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final MemberRepository memberRepository;

    private final MemberService memberService;

    // 스터디 생성 (메서드 내에서 예외가 발생하면 자동으로 rollback)
    @Transactional
    public Study createStudy(StudyCreateRequestDTO dto, String memberId) {

        Member member = memberService.findByMemberId(memberId);

        // Study 엔티티 생성 및 저장
        Study study = toStudyEntity(dto);
        Study savedStudy = studyRepository.save(study);

        // StudyMember 엔티티 생성 및 저장
        StudyMember studyMember = createLeaderMember(savedStudy, member);
        studyMemberRepository.save(studyMember);

        return savedStudy;
    }


    // 스터디 조회
    public StudyInfoDTO getStudyByStudyId(Long studyId) {
        Study study = findStudyByStudyId(studyId);

        return StudyInfoDTO.builder()
                .title(study.getTitle())
                .description(study.getDescription())
                .status(study.getStatus())
                .build();
    }
    // 그룹 정보, 멤버 조회, 게시판 조회
    public StudyWithMembersDTO getStudyInfoWithMembers(Long studyId) {

        Study study = findStudyByStudyId(studyId);

        Pageable pageable = PageRequest.of(0, 5);
        Page<QuestionWithoutAnswerDTO> result = questionRepository
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
        Page<QuestionWithoutAnswerDTO> result = questionRepository
                .findByStudyMember_Study_StudyIdOrderByCreatedAtDesc(studyId, pageable);

        return new MemberAndBoardDTO(
                findMembersByStudyId(studyId),
                result
        );
    }

    // 스터디 일정 조회
    public List<ScheduleWithNicknameDTO> getScheduleByStudyId(Long studyId) {
        List<ScheduleResponseDTO> schedules  = scheduleRepository.findByStudy_StudyId(studyId);

        return schedules.stream()
                .map(schedule -> {
                    String nickname = memberRepository.findById(schedule.getMemberId())
                            .map(Member::getNickname)
                            .orElse("Unknown");
                    return new ScheduleWithNicknameDTO(schedule, nickname);
                })
                .collect(Collectors.toList());
    }

    // 스터디 수정
    public StudyInfoDTO updateStudy(Long studyId, Study studyData, String memberId) throws UnavailableException {

        Study extistingStudy = findStudyByStudyId(studyId);

        memberService.findByMemberId(memberId);

        // 관리자가 해당 스터디의 관리자 권한이 있는지 확인
        StudyMember adminMember = studyMemberRepository.findByMember_MemberIdAndStudy_StudyId(memberId, studyId)
                .orElseThrow(() -> new StudyMemberNotFoundException("Admin StudyMember not found for Member ID " + memberId + " and Study ID " + studyId));

        checkAdminRole(adminMember);  // 관리 권한 체크 메서드 호출

        if(studyData.getTitle() != null) {
            extistingStudy.setTitle(studyData.getTitle());
        }
        if(studyData.getDescription() != null) {
            extistingStudy.setDescription(studyData.getDescription());
        }
        if(studyData.getStatus() != null) {
            extistingStudy.setStatus(studyData.getStatus());
        }
        Study saved = studyRepository.save(extistingStudy);


        return StudyInfoDTO.builder()
                .studyId(saved.getStudyId())
                .description(saved.getDescription())
                .status(saved.getStatus())
                .title(saved.getTitle())
                .build();
    }

    // 스터디 삭제
    public void deleteStudy(String memberId, Long studyId) throws UnavailableException {
        Study extistingStudy = findStudyByStudyId(studyId);

        memberService.findByMemberId(memberId);

        // 관리자가 해당 스터디의 관리자 권한이 있는지 확인
        StudyMember adminMember = studyMemberRepository.findByMember_MemberIdAndStudy_StudyId(memberId, studyId)
                .orElseThrow(() -> new StudyMemberNotFoundException("Admin StudyMember not found for Member ID " + memberId + " and Study ID " + studyId));

        checkAdminRole(adminMember);  // 관리 권한 체크 메서드 호출

        studyRepository.delete(extistingStudy);
    }

    // 스터디 게시판 조회
    public Page<QuestionWithoutAnswerDTO> getBoards(Long studyId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<QuestionWithoutAnswerDTO> result = questionRepository
                .findByStudyMember_Study_StudyIdOrderByCreatedAtDesc(studyId, pageable);


        return new PageImpl<>(result.getContent(), pageable, result.getTotalElements());
    }

    public List<StudyDTO> getAllStudies() {
        List<Study> studies = studyRepository.findAll();

        return studies.stream()
                .map(study -> new StudyDTO(
                        study.getStudyId(),
                        study.getTitle(),
                        study.getDescription(),
                        study.getStatus(),
                        study.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }


    public List<StudyDTO> getStudiesByMemberId(String memberId) {
        List<Long> studyIds = studyMemberRepository.findStudyIdsByMemberId(memberId);

        if (studyIds.isEmpty()) {
            throw new NoSuchElementException("No studies found for the member with ID " + memberId);
        }

        List<Study> studies = studyRepository.findAllByStudyIdIn(studyIds);

        return studies.stream()
                .map(study -> new StudyDTO(
                        study.getStudyId(),
                        study.getTitle(),
                        study.getDescription(),
                        study.getStatus(),
                        study.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    public Study findStudyByStudyId(Long studyId) {
        return studyRepository.findById(studyId)
                .orElseThrow(() -> new StudyNotFoundException(studyId));
    }

    private List<MemberAndStatusRoleDTO> findMembersByStudyId(Long studyId) {
        return studyRepository.findMemberByStudyId(studyId);
    }

    // Leader StudyMember 엔티티 생성 메서드
    private StudyMember createLeaderMember(Study study, Member member) {
        StudyMember studyMember = new StudyMember();
        studyMember.setRole(RoleConstants.ADMIN);
        studyMember.setStatus(StatusConstants.CONFIRM);
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
