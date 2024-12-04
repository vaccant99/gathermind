package woongjin.gatherMind.enums;

import woongjin.gatherMind.constants.ErrorMessages;
import woongjin.gatherMind.exception.notFound.StudyStatusCodeNotFoundException;

public enum StudyStatus {
    OPEN(1),
    CLOSED(2);

    private final int code;

    StudyStatus(int code) {
        this.code = code;
    }

    public int getCode(){
        return code;
    }

    public static StudyStatus fromCode(int code){
        for(StudyStatus status: StudyStatus.values()){
            if(status.code == code) {
                return status;
            }
        }
        throw new StudyStatusCodeNotFoundException(ErrorMessages.STUDY_STATUS_CODE_NOT_FOUND);
    }

}
