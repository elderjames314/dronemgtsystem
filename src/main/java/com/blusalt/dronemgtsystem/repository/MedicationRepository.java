package com.blusalt.dronemgtsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.blusalt.dronemgtsystem.model.Medication;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

    List<Medication> findByIdIn(List<Long> medicationIds);

    Optional<Medication> findById(Long i);

}
