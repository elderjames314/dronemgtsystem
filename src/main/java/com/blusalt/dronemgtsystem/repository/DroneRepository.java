package com.blusalt.dronemgtsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blusalt.dronemgtsystem.model.Drone;

public interface DroneRepository extends JpaRepository<Drone, Long> {
    
}
