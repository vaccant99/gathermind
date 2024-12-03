package woongjin.gatherMind.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woongjin.gatherMind.DTO.*;
import woongjin.gatherMind.constants.ProgressConstants;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.entity.StudyMember;
import woongjin.gatherMind.enums.Role;
import woongjin.gatherMind.enums.MemberStatus;
import woongjin.gatherMind.exception.unauthorized.UnauthorizedActionException;
import woongjin.gatherMind.exception.notFound.MemberNotFoundException;
import woongjin.gatherMind.exception.notFound.StudyNotFoundException;
import woongjin.gatherMind.exception.notFound.StudyMemberNotFoundException;
import woongjin.gatherMind.repository.*;
import woongjin.gatherMind.DTO.StudyDTO;
import woongjin.gatherMind.repository.StudyMemberRepository;
import woongjin.gatherMind.repository.StudyRepository;

import java.util.List;
import java.util.stream.Collectors;

import static woongjin.gatherMind.entity.StudyMember.createStudyMember;
import static woongjin.gatherMind.util.StudyMemberUtils.checkAdminRole;


@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;
    private final QuestionRepository questionRepository;
    private final StudyMemberRepository studyMemberRepository;

    private final CommonLookupService commonLookupService;

    /**
     * 스터디 생성
     *
     * @param dto      생성 요청 DTO
     * @param memberId 생성하는 회원 ID
     * @return 생성된 스터디
     * @throws MemberNotFoundException 회원 ID가 존재하지 않을 경우
     */
    @Transactional
    public Study createStudy(StudyCreateRequestDTO dto, String memberId) {

        Member member = commonLookupService.findByMemberId(memberId);

        Study study = Study.createStudy(dto);
        Study savedStudy = studyRepository.save(study);

        StudyMember studyMember = createStudyMember(savedStudy, member,
                Role.ADMIN, MemberStatus.APPROVED, ProgressConstants.NOT_STARTED);
        studyMemberRepository.save(studyMember);

        return savedStudy;
    }

    /**
     * 스터디 조회
     *
     * @param studyId 스터디 ID
     * @return 스터디 정보 DTO
     * @throws StudyNotFoundException 스터디 ID가 존재하지 않을 경우
     */
    @Transactional(readOnly = true)
    public StudyInfoDTO getStudyByStudyId(Long studyId) {
        Study study = commonLookupService.findStudyByStudyId(studyId);

        return StudyInfoDTO.builder()
                .title(study.getTitle())
                .description(study.getDescription())
                .status(study.getStatus())
                .build();
    }

    /**
     * 스터디 수정
     *
     * @param studyId   수정할 스터디 ID
     * @param studyData 수정할 스터디 정보
     * @param memberId  수정 요청자 ID
     * @return 수정된 스터디 정보 DTO
     * @throws StudyNotFoundException 스터디 ID가 존재하지 않을 경우
     * @throws MemberNotFoundException 회원 ID가 존재하지 않을 경우
     * @throws UnauthorizedActionException 수정 권한이 없을 경우
     */
    @Transactional
    public StudyInfoDTO updateStudy(Long studyId, Study studyData, String memberId) {

        Study extistingStudy = commonLookupService.findStudyByStudyId(studyId);

        commonLookupService.checkMemberExists(memberId);

        // 관리자가 해당 스터디의 관리자 권한이 있는지 확인
        getAdminMember(memberId, studyId);

        updateStudyFields(extistingStudy, studyData);

        Study saved = studyRepository.save(extistingStudy);

        return StudyInfoDTO.builder()
                .studyId(saved.getStudyId())
                .description(saved.getDescription())
                .status(saved.getStatus())
                .title(saved.getTitle())
                .build();
    }

    /**
     * 스터디 삭제
     *
     * @param memberId 삭제 요청자 ID
     * @param studyId  삭제할 스터디 ID
     * @throws StudyNotFoundException 스터디 ID가 존재하지 않을 경우
     * @throws MemberNotFoundException 회원 ID가 존재하지 않을 경우
     * @throws UnauthorizedActionException 삭제 권한이 없을 경우
     */
    @Transactional
    public void deleteStudy(String memberId, Long studyId)  {
        Study extistingStudy = commonLookupService.findStudyByStudyId(studyId);
        commonLookupService.checkMemberExists(memberId);

        getAdminMember(memberId, studyId);

        studyRepository.delete(extistingStudy);
    }


    // 스터디 정보, 멤버 조회, 게시판 조회

    /**
     * 스터디 정보와 멤버, 게시판 조회
     *
     * @param studyId 스터디 ID
     * @return 스터디 정보와 멤버 목록, 게시판 목록 DTO
     * @throws StudyNotFoundException 스터디 ID가 존재하지 않을 경우
     */
    @Transactional(readOnly = true)
    public StudyWithMembersDTO getStudyInfoWithMembers(Long studyId) {

        Study study = commonLookupService.findStudyByStudyId(studyId);

        Pageable pageable = PageRequest.of(0, 5);
        Page<QuestionWithoutAnswerDTO> result = questionRepository
                .findByStudyMember_Study_StudyIdOrderByCreatedAtDesc(studyId, pageable);

        return new StudyWithMembersDTO
                (study.getStudyId(),
                        study.getTitle(),
                        study.getDescription(),
                        studyMemberRepository.countByStudy_StudyIdAndStatus(studyId, MemberStatus.PENDING),
                        findMembersByStudyId(studyId),
                        result
                );
    }

    /**
     * 멤버와 게시판 조회
     *
     * @param studyId 스터디 ID
     * @return 멤버와 게시판 목록 DTO
     * @throws StudyNotFoundException 스터디 ID가 존재하지 않을 경우
     */
    @Transactional(readOnly = true)
    public MemberAndBoardDTO getMembersAndBoard(Long studyId) {

        commonLookupService.findStudyByStudyId(studyId);

        Pageable pageable = PageRequest.of(0, 5);
        Page<QuestionWithoutAnswerDTO> result = questionRepository
                .findByStudyMember_Study_StudyIdOrderByCreatedAtDesc(studyId, pageable);

        return new MemberAndBoardDTO(
                studyMemberRepository.countByStudy_StudyIdAndStatus(studyId, MemberStatus.PENDING),
                findMembersByStudyId(studyId),
                result
        );
    }


    /**
     * 스터디 ID로 스터디 게시판 페이지 단위로 조회합니다.
     *
     * @param studyId 스터디 ID
     * @param page       조회할 페이지 번호
     * @param size       한 페이지에 표시할 답변 개수
     * @return 페이지화된 게시판 목록
     * @throws StudyNotFoundException 스터디 ID가 존재하지 않을 경우
     */
    @Transactional(readOnly = true)
    public Page<QuestionWithoutAnswerDTO> getBoards(Long studyId, int page, int size) {

        commonLookupService.findStudyByStudyId(studyId);
        Pageable pageable = PageRequest.of(page, size);
        Page<QuestionWithoutAnswerDTO> result = questionRepository
                .findByStudyMember_Study_StudyIdOrderByCreatedAtDesc(studyId, pageable);

        return new PageImpl<>(result.getContent(), pageable, result.getTotalElements());
    }

    /**
     * 메인화면에서 모든 스터디 그룹 보이기
     *
     * @return 스터디 목록 DTO
     * @throws StudyNotFoundException 스터디 ID가 존재하지 않을 경우
     */
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


    /**
     * 특정 멤버의 관리자 권한 확인
     *
     * @param memberId 회원 ID
     * @param studyId  스터디 ID
     * @return 관리자 스터디 멤버
     * @throws StudyNotFoundException 스터디 ID가 존재하지 않을 경우
     * @throws MemberNotFoundException 회원 ID가 존재하지 않을 경우
     * @throws UnauthorizedActionException 관리자 권한이 없는 경우
     */
    private StudyMember getAdminMember(String memberId, Long studyId) {
        StudyMember adminMember = studyMemberRepository.findByMember_MemberIdAndStudy_StudyId(memberId, studyId)
                .orElseThrow(() -> new StudyMemberNotFoundException(memberId,studyId));
        checkAdminRole(adminMember);
        return adminMember;
    }

    /**
     * 스터디 필드 업데이트
     *
     * @param study     기존 스터디
     * @param studyData 새로운 데이터
     */
    private void updateStudyFields(Study study, Study studyData) {
        if (studyData.getTitle() != null) {
            study.setTitle(studyData.getTitle());
        }
        if (studyData.getDescription() != null) {
            study.setDescription(studyData.getDescription());
        }
        if (studyData.getStatus() != null) {
            study.setStatus(studyData.getStatus());
        }
    }

    /**
     * 스터디 멤버와 역할 조회
     *
     * @param studyId 스터디 ID
     * @return 스터디 멤버 및 역할 정보 리스트
     */
    public List<MemberAndStatusRoleDTO> findMembersByStudyId(Long studyId) {
        return studyRepository.findMemberByStudyId(studyId);
    }

}
