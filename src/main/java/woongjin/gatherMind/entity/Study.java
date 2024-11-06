package woongjin.gatherMind.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "study")
@Getter
@Setter
public class Study {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyId;

    private String title;
    private String description;
    private LocalDateTime createdAt;
    private String status;

    // Study - StudyMember (1:N)
    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<StudyMember> studyMembers;

    // Study - Question (1:N)
    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<Question> questions;

    // Study - Schedule (1:N)
    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<Schedule> schedules;
}
