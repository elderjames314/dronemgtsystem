package com.blusalt.dronemgtsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blusalt.dronemgtsystem.model.Medication;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
    
}
