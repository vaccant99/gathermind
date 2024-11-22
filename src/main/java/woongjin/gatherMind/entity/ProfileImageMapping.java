package woongjin.gatherMind.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileImageMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileImageMappingId;

    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    private FileMetadata file;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
