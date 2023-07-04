package com.blusalt.dronemgtsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blusalt.dronemgtsystem.enums.DroneState;
import com.blusalt.dronemgtsystem.model.Drone;

public interface DroneRepository extends JpaRepository<Drone, Long> {

    //@Query("SELECT d FROM Drone d WHERE d.state = :state")
    List<Drone> findByState(DroneState state);
    
}
