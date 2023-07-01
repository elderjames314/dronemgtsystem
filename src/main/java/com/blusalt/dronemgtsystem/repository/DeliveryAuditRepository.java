package com.blusalt.dronemgtsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blusalt.dronemgtsystem.model.DeliveryAuditLog;

public interface DeliveryAuditRepository extends JpaRepository<DeliveryAuditLog, Long> {
    
}
