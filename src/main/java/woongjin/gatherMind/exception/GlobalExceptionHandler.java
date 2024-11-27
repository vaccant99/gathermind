package woongjin.gatherMind.exception;

import jakarta.servlet.UnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import woongjin.gatherMind.exception.answer.AnswerNotFoundException;
import woongjin.gatherMind.exception.invalid.InvalidNicknameException;
import woongjin.gatherMind.exception.invalid.InvalidPasswordException;
import woongjin.gatherMind.exception.invalid.InvalidTokenException;
import woongjin.gatherMind.exception.member.MemberNotFoundException;
import woongjin.gatherMind.exception.question.QuestionNotFoundException;
import woongjin.gatherMind.exception.study.StudyNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(StudyNotFoundException.class)
    public ResponseEntity<String> handleStudyNotFoundException(StudyNotFoundException e) {
        logger.warn("StudyNotFoundException occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Study not found");
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<String> handleMemberNotFoundException(MemberNotFoundException e) {
        logger.warn("MemberNotFoundException occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member not found");
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<String> handleQuestionNotFoundException(QuestionNotFoundException e) {
        logger.warn("QuestionNotFoundException occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found");
    }

    @ExceptionHandler(AnswerNotFoundException.class)
    public ResponseEntity<String> handleAnswerNotFoundException(AnswerNotFoundException e) {
        logger.warn("AnswerNotFoundException occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Answer not found");
    }

    @ExceptionHandler(InvalidNicknameException.class)
    public ResponseEntity<String> handleInvalidNicknameException(InvalidNicknameException e) {
        logger.warn("InvalidNicknameException occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nickname Invalid");
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<String> handleInvalidPasswordException(InvalidPasswordException e) {
        logger.warn("InvalidPasswordException occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password Invalid");
    }
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<String> handleInvalidTokenException(InvalidTokenException e) {
        logger.warn("InvalidTokenException occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token Invalid");
    }

    @ExceptionHandler(MissingTokenException.class)
    public ResponseEntity<String> handleMissingTokenException(MissingTokenException e) {
        logger.warn("MissingTokenException occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token Missing");
    }

    // UnavailableException 예외 처리
    @ExceptionHandler(UnavailableException.class)
    public ResponseEntity<String> handleUnavailableException(UnavailableException e) {
        logger.warn("UnavailableException occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unavailable");
    }

    @ExceptionHandler(FileSizeExceededException.class)
    public ResponseEntity<String> handleFileSizeExceededException(FileSizeExceededException e) {
        logger.warn("FileSizeExceededException occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("FileSizeExceeded");
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<String> handleUnauthorizedActionException(UnauthorizedActionException e) {
        logger.warn("UnauthorizedActionException occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UnauthorizedActionException");
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<String> handleDuplicateEmailException(DuplicateEmailException e) {
        logger.warn("DuplicateEmailException occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("DuplicateEmailException");
    }

    @ExceptionHandler(DuplicateMemberIdException.class)
    public ResponseEntity<String> handleDuplicateMemberIdException(DuplicateMemberIdException e) {
        logger.warn("DuplicateMemberIdException occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("DuplicateMemberIdException");
    }

    @ExceptionHandler(DuplicateNicknameException.class)
    public ResponseEntity<String> handleDuplicateNicknameException(DuplicateNicknameException e) {
        logger.warn("DuplicateNicknameException occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("DuplicateNicknameException");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        // 모든 예외에 대한 로그 출력
        logger.warn("Exception occurred: {}", e.getMessage());

        // 사용자 응답 반환
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred. Please try again later.");
    }
}
