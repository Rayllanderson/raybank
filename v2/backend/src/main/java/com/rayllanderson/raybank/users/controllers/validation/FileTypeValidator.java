package com.rayllanderson.raybank.users.controllers.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class FileTypeValidator implements ConstraintValidator<ValidFileType, MultipartFile> {

    private List<String> allowedFileTypes;

    @Override
    public void initialize(ValidFileType constraintAnnotation) {
        allowedFileTypes = Arrays.asList(constraintAnnotation.allowed());
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null) {
            return true;
        }
        return allowedFileTypes.contains(file.getContentType());
    }
}