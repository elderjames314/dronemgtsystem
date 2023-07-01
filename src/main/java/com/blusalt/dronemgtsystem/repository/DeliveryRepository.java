package com.blusalt.dronemgtsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blusalt.dronemgtsystem.model.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    
}
