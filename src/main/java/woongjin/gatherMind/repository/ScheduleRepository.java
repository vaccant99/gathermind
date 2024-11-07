package woongjin.gatherMind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import woongjin.gatherMind.DTO.ScheduleDTO;
import woongjin.gatherMind.entity.Schedule;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<ScheduleDTO> findByStudy_StudyId(Long studyId);
}
