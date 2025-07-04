package com.khang.databackup.repository;

import com.khang.databackup.entity.BackupHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackupHistoryRepository extends JpaRepository<BackupHistory, Long> {

}
