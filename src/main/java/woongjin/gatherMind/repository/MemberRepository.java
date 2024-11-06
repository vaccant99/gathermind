package woongjin.gatherMind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import woongjin.gatherMind.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, String> {

    @Query(value = """
    SELECT * From Member WHERE MEMBER_ID IN ( 
    SELECT MEMBER_ID FROM group_membership 
    WHERE meeting_id = :meetingId
    GROUP BY MEMBER_ID 
)
""",nativeQuery = true)
    List<Member> findMemberByMeetingId(@Param("meetingId") Long meetingId);

    @Query(value = """
            SELECT
            m.member_id,
            m.nickname,
            am.appointment_id,
            COUNT(*) AS late_count,
            TIMESTAMPDIFF(MINUTE, arrive_time, am.appointment_time ) as  late_time
            FROM group_membership gm
            JOIN appointment am ON gm.appointment_id = am.appointment_id
            JOIN member m ON gm.member_id = m.member_id
            WHERE gm.is_late = true AND gm.meeting_id = :meetingId
            GROUP BY am.appointment_id, m.nickname, m.member_id
            ORDER BY late_count, late_time DESC
            LIMIT 5;
"""
            ,nativeQuery = true)
    List<Object[]> findMemberByMeetingIdOrderByLate(@Param("meetingId") Long meetingId);
}
