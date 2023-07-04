package com.blusalt.dronemgtsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blusalt.dronemgtsystem.enums.DroneStates;
import com.blusalt.dronemgtsystem.model.Drone;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {

    @Query(value = "SELECT * FROM drones d WHERE d.state = :state", nativeQuery = true)
    List<Drone> findByState(@Param("state") String state);

}
