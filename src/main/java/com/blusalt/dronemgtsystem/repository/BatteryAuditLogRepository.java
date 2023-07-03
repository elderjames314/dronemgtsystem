package com.blusalt.dronemgtsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blusalt.dronemgtsystem.model.BatteryAuditLog;

public interface BatteryAuditLogRepository extends JpaRepository<BatteryAuditLog, Long> {
    
}
