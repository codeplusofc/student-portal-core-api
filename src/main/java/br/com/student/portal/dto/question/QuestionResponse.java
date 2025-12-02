package br.com.student.portal.dto.question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionResponse {

    private String id;              // ✅ UUID convertido para String
    private String title;
    private String content;
    private String authorId;
    private String authorName;
    private Integer answerCount;
    private String createdAt;       // ✅ LocalDateTime convertido para String
    private String updatedAt;       // ✅ LocalDateTime convertido para String
}