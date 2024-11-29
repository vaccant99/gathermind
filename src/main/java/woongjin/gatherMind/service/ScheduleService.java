package woongjin.gatherMind.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woongjin.gatherMind.DTO.ScheduleDTO;
import woongjin.gatherMind.DTO.ScheduleResponseDTO;
import woongjin.gatherMind.DTO.ScheduleWithNicknameDTO;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.entity.Schedule;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.exception.notFound.MemberNotFoundException;
import woongjin.gatherMind.exception.notFound.ScheduleNotFoundException;
import woongjin.gatherMind.exception.notFound.StudyNotFoundException;

import woongjin.gatherMind.repository.MemberRepository;
import woongjin.gatherMind.repository.ScheduleRepository;


import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    private final CommonLookupService commonLookupService;

    /**
     * 일정 생성
     *
     * @param scheduleDTO 생성할 일정 정보
     * @param memberId    생성하는 회원 ID
     * @return 생성된 일정
     * @throws StudyNotFoundException 스터디 ID가 존재하지 않을 경우
     * @throws MemberNotFoundException 회원 ID가 존재하지 않을 경우
     */
    @Transactional
    public Schedule createSchedule(ScheduleDTO scheduleDTO, String memberId) {

        Study study = commonLookupService.findStudyByStudyId(scheduleDTO.getStudyId());

        Member member = commonLookupService.findByMemberId(memberId);

        Schedule schedule = toEntity(scheduleDTO);
        schedule.setStudy(study);
        schedule.setMemberId(member.getMemberId());

        return this.scheduleRepository.save(schedule);
    }

    /**
     * 일정 수정
     *
     * @param scheduleId  수정할 일정 ID
     * @param scheduleDTO 수정할 정보
     * @return 수정된 일정
     * @throws ScheduleNotFoundException 일정 ID가 존재하지 않을 경우
     */
    @Transactional
    public Schedule updateSchedule(Long scheduleId, ScheduleDTO scheduleDTO) {

        Schedule schedule = findByScheduleId(scheduleId);

        schedule.setTitle(scheduleDTO.getTitle());
        schedule.setDateTime(scheduleDTO.getDateTime());
        schedule.setLocation(scheduleDTO.getLocation());
        schedule.setDescription(scheduleDTO.getDescription());

        return this.scheduleRepository.save(schedule);
    }

    /**
     * 일정 삭제
     *
     * @param scheduleId 삭제할 일정 ID
     * @throws ScheduleNotFoundException 일정 ID가 존재하지 않을 경우
     */
    @Transactional
    public Schedule deleteSchedule(Long scheduleId) {

        Schedule schedule = findByScheduleId(scheduleId);

        this.scheduleRepository.delete(schedule);

        return schedule;
    }

    /**
     * 일정 ID로 일정 조회
     *
     * @param scheduleId 조회할 일정 ID
     * @return 일정 객체
     * @throws ScheduleNotFoundException 일정 ID가 존재하지 않을 경우
     */
    public Schedule findByScheduleId(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() ->
                new ScheduleNotFoundException(scheduleId));
    }


    /**
     * 스터디 일정 조회
     *
     * @param studyId 스터디 ID
     * @return 일정 및 생성자 닉네임 DTO 리스트
     */
    @Transactional(readOnly = true)
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

    /**
     * DTO를 Entity로 변환
     *
     * @param dto 일정 정보 DTO
     * @return 변환된 일정 엔티티
     */
    private Schedule toEntity(ScheduleDTO dto) {
        Schedule schedule = new Schedule();
        schedule.setTitle(dto.getTitle());
        schedule.setDescription(dto.getDescription());
        schedule.setDateTime(dto.getDateTime());
        schedule.setLocation(dto.getLocation());
        return schedule;
    }
}
