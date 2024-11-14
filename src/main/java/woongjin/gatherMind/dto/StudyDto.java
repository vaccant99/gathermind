package woongjin.gatherMind.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class StudyDto {
    private Long studyId;
    private String title;
    private String description;
    private boolean status;
    private LocalDateTime createdAt;



}
