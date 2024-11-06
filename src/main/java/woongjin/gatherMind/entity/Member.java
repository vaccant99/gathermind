package woongjin.gatherMind.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id
    private String memberId;

    @Column(unique = true)
    private String nickname;

    private String password;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    //    private String email;
//    private String phone

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<GroupMembership> groupMembership;
}
