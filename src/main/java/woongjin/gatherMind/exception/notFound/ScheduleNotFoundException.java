package woongjin.gatherMind.exception.notFound;

import woongjin.gatherMind.constants.ErrorMessages;

public class ScheduleNotFoundException extends NotFoundException {

    public ScheduleNotFoundException(String message) { super(message); }

    public ScheduleNotFoundException (Long scheduleId) {
        super("Schedule ID: " + scheduleId + " not found");
    }

    public ScheduleNotFoundException() {
        super(ErrorMessages.SCHEDULE_NOT_FOUND);
    }
}
