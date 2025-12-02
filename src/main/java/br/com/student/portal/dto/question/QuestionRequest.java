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
public class QuestionRequest {

    private String title;
    private String content;
    private UserEntity userEntity;
}