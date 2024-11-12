package woongjin.gatherMind.repository;

import woongjin.gatherMind.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}