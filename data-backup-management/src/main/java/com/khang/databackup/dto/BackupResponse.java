package com.khang.databackup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BackupResponse {
    private Long id;
    private String backupTime; // ISO datetime string
    private String fullName;
    private String backupPath;
    private String note;
}
