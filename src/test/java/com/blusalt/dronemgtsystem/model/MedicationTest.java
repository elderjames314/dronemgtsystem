package com.blusalt.dronemgtsystem.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.blusalt.dronemgtsystem.exceptions.InvalidRequestException;

public class MedicationTest {

    @Test
    void testSetName_ValidName() {
        Medication medication = new Medication();
        String validName = "Medication-123";

        medication.setName(validName);

        assertEquals(validName, medication.getName());
    }

    @Test
    void testSetName_InvalidName() {
        Medication medication = new Medication();
        String invalidName = "Medication$#@";

        assertThrows(InvalidRequestException.class, () -> medication.setName(invalidName));
    }

    @Test
    void testSetName_NullName() {
        Medication medication = new Medication();
        String nullName = null;

        assertThrows(NullPointerException.class, () -> medication.setName(nullName));
    }

    @Test
    void testSetCode_ValidCode() {
        Medication medication = new Medication();
        String validCode = "ABC_123";

        medication.setCode(validCode);

        assertEquals(validCode, medication.getCode());
    }

    @Test
    void testSetCode_InvalidCode() {
        Medication medication = new Medication();
        String invalidCode = "abc123";

        assertThrows(InvalidRequestException.class, () -> medication.setCode(invalidCode));
    }

    @Test
    void testSetCode_NullCode() {
        Medication medication = new Medication();
        String nullCode = null;

        assertThrows(NullPointerException.class, () -> medication.setCode(nullCode));
    }
}
