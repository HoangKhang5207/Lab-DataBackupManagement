package com.khang.databackup.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BackupPathValidator implements ConstraintValidator<ValidBackupPath, String> {
    @Override
    public boolean isValid(String path, ConstraintValidatorContext context) {
        if (path == null || path.isBlank()) {
            return false;
        }
        // Kiểm tra đuôi .bak
        if (!path.toLowerCase().endsWith(".bak")) {
            return false;
        }
        // Kiểm tra file tồn tại
        try {
            return Files.exists(Paths.get(path));
        } catch (Exception e) {
            return false;
        }
    }
}
