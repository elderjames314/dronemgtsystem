package com.blusalt.dronemgtsystem.operation.drone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.blusalt.dronemgtsystem.dtos.DroneDto;
import com.blusalt.dronemgtsystem.enums.DroneModel;
import com.blusalt.dronemgtsystem.model.Drone;
import com.blusalt.dronemgtsystem.repository.DroneRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@ExtendWith(MockitoExtension.class)
public class DroneBaseFactoryTest {

    @Mock
    private DroneRepository droneRepository;

    @InjectMocks
    private DroneBaseFactory droneFactory;

    private DroneDto droneDto;
    private Drone mockedDrone;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        droneDto = new DroneDto();
        droneDto.setSerialNumber("12345");
        droneDto.setBatteryCapacity(100);
        droneDto.setWeightLimit("50");

        mockedDrone = new Drone();
        mockedDrone.setId(1L);
        mockedDrone.setBatteryCapacity(100);
        mockedDrone.setSerialNumber("12345");
        mockedDrone.setWeightLimit(50);
    }

    @Test
    void testCreateLightweightDrone() {

        mockedDrone.setModel(DroneModel.LIGHTWEIGHT);

        when(droneRepository.save(ArgumentMatchers.any(Drone.class))).thenReturn(mockedDrone);

        Drone drone = droneFactory.createLightweightDrone(droneDto);

        assertEquals(DroneModel.LIGHTWEIGHT, drone.getModel());

        verify(droneRepository).save(ArgumentMatchers.any(Drone.class));
    }

    @Test
    void testCreateMiddleweightDrone() {
        mockedDrone.setModel(DroneModel.MIDDLEWEIGHT);

        when(droneRepository.save(ArgumentMatchers.any(Drone.class))).thenReturn(mockedDrone);

        Drone drone = droneFactory.createMiddleweightDrone(droneDto);

        assertEquals(DroneModel.MIDDLEWEIGHT, drone.getModel());

        verify(droneRepository).save(ArgumentMatchers.any(Drone.class));
    }

    @Test
    void testCreateHeavyweightDrone() {
        mockedDrone.setModel(DroneModel.HEAVYWEIGHT);

        when(droneRepository.save(ArgumentMatchers.any(Drone.class))).thenReturn(mockedDrone);

        Drone drone = droneFactory.createHeavyweightDrone(droneDto);

        assertEquals(DroneModel.HEAVYWEIGHT, drone.getModel());

        verify(droneRepository).save(ArgumentMatchers.any(Drone.class));
    }

    @Test
    void testCreateCruiserweightDrone() {
        mockedDrone.setModel(DroneModel.CRUISERWEIGHT);

        when(droneRepository.save(ArgumentMatchers.any(Drone.class))).thenReturn(mockedDrone);

        Drone drone = droneFactory.createCruiserweightDrone(droneDto);

        assertEquals(DroneModel.CRUISERWEIGHT, drone.getModel());

        verify(droneRepository).save(ArgumentMatchers.any(Drone.class));
    }
}
