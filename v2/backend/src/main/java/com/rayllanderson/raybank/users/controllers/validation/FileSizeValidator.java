package com.rayllanderson.raybank.users.controllers.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileSizeValidator implements ConstraintValidator<ValidFileSize, MultipartFile> {

    private long maxSize;

    @Override
    public void initialize(ValidFileSize constraintAnnotation) {
        maxSize = constraintAnnotation.maxSize();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null) {
            return true;
        }
        return file.getSize() <= maxSize;
    }
}
