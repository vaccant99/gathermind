package woongjin.gatherMind.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import woongjin.gatherMind.exception.conflict.ConflictException;
import woongjin.gatherMind.exception.file.FileException;
import woongjin.gatherMind.exception.invalid.InvalidRequestException;
import woongjin.gatherMind.exception.notFound.*;
import woongjin.gatherMind.exception.studyMember.StudyMemberAlreadyExistsException;
import woongjin.gatherMind.exception.studyMember.StudyMemberException;
import woongjin.gatherMind.exception.unauthorized.UnauthorizedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // NotFound 계열 처리
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        logger.warn("NotFoundException occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    // InvalidRequest 계열 처리
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<String> handleInvalidRequestException(InvalidRequestException e) {
        logger.warn("InvalidRequestException occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    // Conflict 계열 처리
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> handleConflictException(ConflictException e) {
        logger.warn("ConflictException occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    // Unauthorized 계열 처리
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException e) {
        logger.warn("UnauthorizedException occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    // file 계열 처리
    @ExceptionHandler(FileException.class)
    public ResponseEntity<String> handleFileException(FileException e) {
        logger.warn("FileException occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(StudyMemberAlreadyExistsException.class)
    public ResponseEntity<String> handleStudyMemberAlreadyExistsException(StudyMemberAlreadyExistsException e) {
        logger.warn("StudyMemberAlreadyExistsException occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    // studyMember 계열 처리
    @ExceptionHandler(StudyMemberException.class)
    public ResponseEntity<String> handleStudyMemberException(StudyMemberException e) {
        logger.warn("StudyMemberException occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    // 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        logger.error("Unexpected exception occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred. Please try again later.");
    }
}
