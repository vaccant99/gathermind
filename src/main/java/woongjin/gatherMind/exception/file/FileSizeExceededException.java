package woongjin.gatherMind.exception.file;

import woongjin.gatherMind.constants.ErrorMessages;

public class FileSizeExceededException extends FileException {

    public FileSizeExceededException(String message) {
        super(message);
    }

//    public FileSizeExceededException(String message, Throwable cause) {
//        super(message,cause);
//    }

    public FileSizeExceededException() {
        super(ErrorMessages.FILE_SIZE_EXCEED);
    }
}
