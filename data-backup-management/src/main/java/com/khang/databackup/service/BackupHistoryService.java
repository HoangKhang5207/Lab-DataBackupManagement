package com.khang.databackup.service;

import com.khang.databackup.dto.BackupRequest;
import com.khang.databackup.dto.BackupResponse;

import java.util.List;

public interface BackupHistoryService {
    List<BackupResponse> findAll();

    BackupResponse findById(Long id);

    BackupResponse create(BackupRequest dto);

    BackupResponse update(Long id, BackupRequest dto);

    void delete(Long id);

    byte[] exportToExcel(); // trả về mảng bytes file .xlsx
}
