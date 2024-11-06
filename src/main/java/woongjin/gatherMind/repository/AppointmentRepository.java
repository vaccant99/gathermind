package woongjin.gatherMind.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import woongjin.gatherMind.entity.Appointment;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query(value = """
    SELECT * From appointment WHERE appointment_id IN ( 
    SELECT appointment_id FROM group_membership 
    WHERE meeting_id = :meetingId
    GROUP BY appointment_id 
)
""", nativeQuery = true)
    List<Appointment> findAppointmentByMeetingId(@Param("meetingId") Long meetingId);

    @Query(value = """
            SELECT ap.appointment_id,
                   ap.appointment_created_id,
                   ap.appointment_name,
                   ap.appointment_status,
                   ap.appointment_time,
                   ap.created_at,
                   ap.location,
                   ap.penalty,
                   MAX(CASE WHEN gm.member_id = :memberId THEN TRUE ELSE FALSE END) AS is_attend
            FROM appointment ap
            JOIN group_membership gm ON ap.appointment_id = gm.appointment_id
            WHERE ap.appointment_id IN (
                SELECT appointment_id
                FROM group_membership
                WHERE meeting_id = :meetingId
                GROUP BY appointment_id
            )
            GROUP BY ap.appointment_id, ap.appointment_created_id, ap.appointment_name,
                                                                   ap.appointment_status, ap.appointment_time, ap.created_at,
                                                                   ap.location, ap.penalty
                                                                   ORDER BY appointment_time DESC;
""",
            countQuery = """
        SELECT COUNT(*)
        FROM appointment ap
        JOIN group_membership gm ON ap.appointment_id = gm.appointment_id
        WHERE ap.appointment_id IN (
            SELECT appointment_id
            FROM group_membership
            WHERE meeting_id = :meetingId
            GROUP BY appointment_id
        )
        """,
            nativeQuery = true)
    Page<Object[]> findAppointmentAndAttendCheckByMeetingIdAndMemberId(
            @Param("meetingId") Long meetingId,
            @Param("memberId")String memberId,
            Pageable pageable);
}
