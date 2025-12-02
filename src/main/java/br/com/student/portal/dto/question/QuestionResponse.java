package br.com.student.portal.dto.question;

import br.com.student.portal.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionResponse {
    private String id;
    private String title;
    private String content;
    private UserEntity author;
    private Integer answerCount;
    private String createdAt;
    private String updatedAt;
}