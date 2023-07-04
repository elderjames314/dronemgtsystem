package com.blusalt.dronemgtsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blusalt.dronemgtsystem.model.BatteryAuditLog;

@Repository
public interface BatteryAuditLogRepository extends JpaRepository<BatteryAuditLog, Long> {
    
}
