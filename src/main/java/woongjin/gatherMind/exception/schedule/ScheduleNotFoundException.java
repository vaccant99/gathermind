package woongjin.gatherMind.exception.schedule;

public class ScheduleNotFoundException extends RuntimeException {

    public ScheduleNotFoundException(String message) { super(message); }

    public ScheduleNotFoundException(String message, Throwable cause) {
        super(message,cause);
    }

    public ScheduleNotFoundException (Long scheduleId) {
        super("Schedule ID: " + scheduleId + " not found");
    }

    public ScheduleNotFoundException() {
        super("Schedule not found");
    }
}
