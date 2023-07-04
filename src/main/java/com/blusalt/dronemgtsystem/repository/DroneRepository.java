package com.blusalt.dronemgtsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blusalt.dronemgtsystem.enums.DroneState;
import com.blusalt.dronemgtsystem.model.Drone;

public interface DroneRepository extends JpaRepository<Drone, Long> {

    @Query(value = "SELECT * FROM Drone d WHERE d.state = ?1", nativeQuery = true)
    List<Drone> findByState(@Param("state") DroneState state);

}
