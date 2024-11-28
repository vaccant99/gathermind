package woongjin.gatherMind.exception.file;

public class FileSizeExceededException extends RuntimeException {

    public FileSizeExceededException(String message) {
        super(message);
    }

    public FileSizeExceededException(String message, Throwable cause) {
        super(message,cause);
    }

    public FileSizeExceededException() {
        super("File size exceeds the maximum allowed limit (10MB).");
    }
}
