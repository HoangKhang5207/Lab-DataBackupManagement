package com.khang.databackup.controller;

import com.khang.databackup.dto.BackupRequest;
import com.khang.databackup.dto.BackupResponse;
import com.khang.databackup.service.BackupHistoryService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/backups")
@RequiredArgsConstructor
public class BackupHistoryController {
    private final BackupHistoryService backupHistoryService;

    @GetMapping
    @Operation(summary = "Get all backup history records")
    public ResponseEntity<List<BackupResponse>> getAll() {
        return ResponseEntity.ok(backupHistoryService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a backup history record by ID")
    public ResponseEntity<BackupResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(backupHistoryService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new backup history record")
    public ResponseEntity<BackupResponse> create(@Valid @RequestBody BackupRequest dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(backupHistoryService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing backup history record")
    public ResponseEntity<BackupResponse> update(@PathVariable Long id,
            @Valid @RequestBody BackupRequest dto) {
        return ResponseEntity.ok(backupHistoryService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a backup history record by ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        backupHistoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/export")
    @Operation(summary = "Export backup history to Excel file")
    public ResponseEntity<byte[]> exportExcel() {
        byte[] data = backupHistoryService.exportToExcel();
        String filename = "backup_history_" +
                LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + ".xlsx";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(data);
    }
}
