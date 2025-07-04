package com.khang.databackup.dto;

import com.khang.databackup.validation.ValidBackupPath;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BackupRequest {
    private Long id;

    @NotNull(message = "Thời điểm backup không được để trống")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$", message = "backupTime phải theo định dạng ISO (yyyy-MM-dd'T'HH:mm:ss)")
    private String backupTime; // ISO datetime string

    @NotNull(message = "User ID không được để trống")
    private Long userId;

    @NotBlank(message = "Đường dẫn file backup không được để trống")
    @Size(max = 255, message = "Đường dẫn tối đa 255 ký tự")
    @ValidBackupPath
    private String backupPath;

    @Size(max = 255, message = "Ghi chú tối đa 255 ký tự")
    private String note;
}
