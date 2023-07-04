package com.blusalt.dronemgtsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blusalt.dronemgtsystem.model.Medication;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

    @Query(value = "SELECT * FROM Medication m WHERE m.id IN ?1", nativeQuery = true)
    List<Medication> findByIdIn(List<Long> medicationIds);

    Optional<Medication> findById(Long i);

}
