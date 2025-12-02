package br.com.student.portal.validation;

import br.com.student.portal.entity.FileEntity;

import br.com.student.portal.repository.FileRepository;
import org.springframework.stereotype.Component;

import static br.com.student.portal.validation.FieldValidator.validateRequiredField;
import static io.micrometer.common.util.StringUtils.isEmpty;

@Component
public class FileValidator {

    public void validateName(String name) {
        if (isEmpty(name)) {
            validateRequiredField(name, "Name");
        }
    }

    public void validateUrl(String url) {
        if (isEmpty(url)) {
            validateRequiredField(url, "Url");
        }
    }

    public void validateFields(FileEntity fileEntity) {
        validateName(fileEntity.getName());
        validateUrl(fileEntity.getUrl());

    }


}

