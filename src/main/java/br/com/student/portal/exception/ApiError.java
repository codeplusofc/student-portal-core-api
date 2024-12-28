package br.com.student.portal.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {
    private Integer statusCode;
    private String error;
    private Long timestamp;
}
