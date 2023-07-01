# Software Engineering Documentation

## Introduction
This software engineering documentation describes the design and architecture of a drone management system for the delivery of medications. The system aims to efficiently manage a fleet of 10 drones capable of delivering small loads of medications to locations with difficult access. The system will handle various aspects such as drone management, medication tracking, and delivery status monitoring.

## Requirements
The following requirements have been identified for the drone management system:

### Fleet Management
- The system should support the management of a fleet of 10 drones.
- Each drone should be uniquely identified by a serial number.
- Each drone should have a model classification (Lightweight, Middleweight, Cruiserweight, or Heavyweight).
- Each drone should have a weight limit (maximum 500 grams).
- Each drone should have a battery capacity represented as a percentage.
- Each drone should have a state indicating its current operational status (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, or RETURNING).

### Medication Management
- The system should support the management of medications for delivery.
- Each medication should have a unique name.
- Each medication should have a weight.
- Each medication should have a code for identification purposes.
- Each medication should have an associated image representing the medication case.

### Drone Operations
- The system should facilitate the assignment of medications to drones for delivery.
- Drones should be able to load medications based on weight limits and battery capacity.
- Drones should be able to deliver loaded medications to designated locations.
- Drones should be able to update their states as they progress through various stages of the delivery process.

### Tracking and Monitoring
- The system should provide real-time tracking and monitoring of drone operations.
- The system should allow users to track the status of a specific drone.
- The system should allow users to monitor the delivery status of medications.

## Design Considerations

### Design Patterns
To design the drone management system, the following design patterns can be considered:

- Factory Pattern: The Factory pattern can be used to create instances of drones and medications. It allows for flexible object creation without specifying the exact class of the object that will be created.
- Observer Pattern: The Observer pattern can be used to implement tracking and monitoring functionality. The drones and medications can be observed by interested parties, such as users or administrators, who can receive updates about the state changes and progress of the drones and medications.
- State Pattern: The State pattern can be applied to manage the state transitions of the drones. Each drone can have different states (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING), and the State pattern helps in encapsulating the behavior associated with each state and managing the transitions between states.

### Software Architecture Design
The drone management system can be designed using a layered architecture, specifically the Model-View-Controller (MVC) pattern. This pattern separates the system into three major components:

- Model: The Model component represents the data and business logic of the system. It includes classes such as Drone, Medication, and the DroneManager responsible for managing the fleet of drones.
- View: The View component handles the presentation and user interface of the system. It includes the user interfaces for tracking drones, monitoring delivery status, and managing medications.
- Controller: The Controller component acts as an intermediary between the Model and View components. It receives user input from the View, performs necessary operations on the Model, and updates the View accordingly. It includes classes such as DroneController and MedicationController.

The layered architecture ensures separation of concerns and modularity, making the system easier to maintain and extend.
