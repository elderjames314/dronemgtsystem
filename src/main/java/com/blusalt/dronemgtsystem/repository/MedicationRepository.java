package com.blusalt.dronemgtsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blusalt.dronemgtsystem.model.Medication;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

    //@Query("SELECT m FROM Medication m WHERE m.id IN :medicationIds")
    List<Medication> findByIdIn(List<Long> medicationIds);

}
