package woongjin.gatherMind.DTO;

import java.time.LocalDateTime;

public class QuestionDTO {

    private Long questionId;
    private String content;
    private String title;
    private String memberId;
    private String studyTitle;
    private LocalDateTime createdAt;

    // 필요한 경우 기본 생성자 추가
    public QuestionDTO() {
    }

    // 필요한 필드를 포함한 생성자 추가
    public QuestionDTO(Long questionId, String content, String title, String memberId, String studyTitle) {
        this.questionId = questionId;
        this.content = content;
        this.title = title;
        this.memberId = memberId;
        this.studyTitle = studyTitle;
    }

    // Getters and Setters
    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getStudyTitle() {
        return studyTitle;
    }

    public void setStudyTitle(String studyTitle) {
        this.studyTitle = studyTitle;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
