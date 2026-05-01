package com.cartracker.repository;

import com.cartracker.model.Vehicle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * VehicleRepository manages persistence of vehicles to/from text files.
 */
@Repository
public class VehicleRepository extends FileBasedRepository<Vehicle> {

    public VehicleRepository(@Value("${app.data.vehicles.path:data/vehicles.txt}") String vehicleFilePath) {
        super(vehicleFilePath);
    }

    @Override
    protected String getHeader() {
        return "ID|Name|Number|Make|Model|Year|Type";
    }

    @Override
    protected Vehicle parseRecord(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length < 7) return null;

            Vehicle vehicle = new Vehicle();
            vehicle.setId(parts[0]);
            vehicle.setName(parts[1]);
            vehicle.setNumber(parts[2]);
            vehicle.setMake(parts[3]);
            vehicle.setModel(parts[4]);
            vehicle.setYear(Integer.parseInt(parts[5]));
            vehicle.setType(parts[6]);

            return vehicle;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected String recordToString(Vehicle record) {
        return record.toString();
    }

    public Optional<Vehicle> findById(String id) {
        return findAll().stream()
                .filter(v -> v.getId().equals(id))
                .findFirst();
    }

    public List<Vehicle> findByType(String type) {
        return findAll().stream()
                .filter(v -> v.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    public synchronized void update(Vehicle vehicle) {
        List<Vehicle> vehicles = findAll();
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getId().equals(vehicle.getId())) {
                vehicles.set(i, vehicle);
                saveAll(vehicles);
                return;
            }
        }
    }

    public synchronized void deleteById(String id) {
        List<Vehicle> vehicles = findAll();
        vehicles.removeIf(v -> v.getId().equals(id));
        saveAll(vehicles);
    }
}
