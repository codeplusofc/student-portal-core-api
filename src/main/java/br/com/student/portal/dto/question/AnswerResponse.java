package br.com.student.portal.dto.question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerResponse {

    private String id;              // ✅ UUID convertido para String
    private String questionId;      // ✅ UUID convertido para String
    private String authorId;
    private String authorName;
    private String content;
    private String createdAt;       // ✅ LocalDateTime convertido para String
    private String updatedAt;       // ✅ LocalDateTime convertido para String
}