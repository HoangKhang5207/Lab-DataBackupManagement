package com.khang.databackup.service.impl;

import com.khang.databackup.service.BackupHistoryService;

import com.khang.databackup.dto.BackupRequest;
import com.khang.databackup.dto.BackupResponse;
import com.khang.databackup.entity.BackupHistory;
import com.khang.databackup.entity.User;
import com.khang.databackup.exception.ResourceNotFoundException;
import com.khang.databackup.repository.BackupHistoryRepository;
import com.khang.databackup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BackupHistoryServiceImpl implements BackupHistoryService {
    private final BackupHistoryRepository backupHistoryRepository;
    private final UserRepository userRepository;

    @Override
    public List<BackupResponse> findAll() {
        return backupHistoryRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public BackupResponse findById(Long id) {
        return backupHistoryRepository.findById(id).map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Backup not found " + id));
    }

    @Override
    public BackupResponse create(BackupRequest dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found " + dto.getUserId()));
        BackupHistory bh = BackupHistory.builder()
                .backupTime(LocalDateTime.parse(dto.getBackupTime()))
                .user(user)
                .backupPath(dto.getBackupPath())
                .note(dto.getNote())
                .build();
        return toDto(backupHistoryRepository.save(bh));
    }

    @Override
    public BackupResponse update(Long id, BackupRequest dto) {
        BackupHistory bh = backupHistoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Backup not found " + id));
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found " + dto.getUserId()));
            bh.setUser(user);
        }
        bh.setBackupTime(LocalDateTime.parse(dto.getBackupTime()));
        bh.setBackupPath(dto.getBackupPath());
        bh.setNote(dto.getNote());
        return toDto(backupHistoryRepository.save(bh));
    }

    @Override
    public void delete(Long id) {
        backupHistoryRepository.deleteById(id);
    }

    @Override
    public byte[] exportToExcel() {
        List<BackupHistory> list = backupHistoryRepository.findAll();
        try (XSSFWorkbook wb = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            XSSFSheet sheet = wb.createSheet("Backups");
            int rowIdx = 0;
            // Header
            XSSFRow header = sheet.createRow(rowIdx++);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Time");
            header.createCell(2).setCellValue("User");
            header.createCell(3).setCellValue("Path");
            header.createCell(4).setCellValue("Note");

            for (BackupHistory bh : list) {
                XSSFRow r = sheet.createRow(rowIdx++);
                r.createCell(0).setCellValue(bh.getId());
                r.createCell(1).setCellValue(bh.getBackupTime().toString());
                r.createCell(2).setCellValue(bh.getUser().getFirstName() + " " + bh.getUser().getLastName());
                r.createCell(3).setCellValue(bh.getBackupPath());
                r.createCell(4).setCellValue(bh.getNote());
            }

            wb.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error when generating Excel", e);
        }
    }

    private BackupResponse toDto(BackupHistory bh) {
        return new BackupResponse(
                bh.getId(),
                bh.getBackupTime().toString(),
                bh.getUser().getFirstName() + " " + bh.getUser().getLastName(),
                bh.getBackupPath(),
                bh.getNote());
    }
}
