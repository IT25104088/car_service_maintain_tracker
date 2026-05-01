package com.cartracker.service;

import com.cartracker.model.Vehicle;
import com.cartracker.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * VehicleService provides business logic for vehicles.
 */
@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle createVehicle(Vehicle vehicle) {
        String id = UUID.randomUUID().toString();
        vehicle.setId(id);
        vehicleRepository.save(vehicle);
        return vehicle;
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> getVehicleById(String id) {
        return vehicleRepository.findById(id);
    }

    public List<Vehicle> getVehiclesByType(String type) {
        return vehicleRepository.findByType(type);
    }

    public Vehicle updateVehicle(String id, Vehicle vehicle) {
        Optional<Vehicle> existingVehicle = vehicleRepository.findById(id);
        if (existingVehicle.isPresent()) {
            vehicle.setId(id);
            vehicleRepository.update(vehicle);
            return vehicle;
        }
        throw new RuntimeException("Vehicle not found with id: " + id);
    }

    public void deleteVehicle(String id) {
        vehicleRepository.deleteById(id);
    }
}
