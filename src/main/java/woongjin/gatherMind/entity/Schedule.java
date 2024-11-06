package woongjin.gatherMind.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "schedule")
@Getter
@Setter
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    private Long studyId;
    private String title;
    private String description;
    private LocalDateTime dateTime;
    private String location;

    // Schedule - Study (N:1)
    @ManyToOne
    @JoinColumn(name = "study_id")
    private Study study;
}
