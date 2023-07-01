package com.blusalt.dronemgtsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blusalt.dronemgtsystem.model.DeliveryAudit;

public interface DeliveryAuditRepository extends JpaRepository<DeliveryAudit, Long> {
    
}
