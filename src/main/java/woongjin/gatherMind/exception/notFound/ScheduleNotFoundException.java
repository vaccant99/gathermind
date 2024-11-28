package woongjin.gatherMind.exception.notFound;

public class ScheduleNotFoundException extends NotFoundException {

    public ScheduleNotFoundException(String message) { super(message); }

    public ScheduleNotFoundException (Long scheduleId) {
        super("Schedule ID: " + scheduleId + " not found");
    }

    public ScheduleNotFoundException() {
        super("Schedule not found");
    }
}
