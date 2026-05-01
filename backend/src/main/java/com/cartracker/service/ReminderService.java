package com.cartracker.service;

import com.cartracker.model.ServiceRecord;
import com.cartracker.model.ServiceReminder;
import com.cartracker.model.Vehicle;
import com.cartracker.repository.ServiceRecordRepository;
import com.cartracker.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ReminderService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ServiceRecordRepository serviceRecordRepository;

    public Map<String, Object> getReminder(String vehicleId, Integer currentMileageOverride) {
        Optional<Vehicle> vehicleOpt = vehicleRepository.findById(vehicleId);
        if (vehicleOpt.isEmpty()) {
            throw new RuntimeException("Vehicle not found with id: " + vehicleId);
        }
        Vehicle vehicle = vehicleOpt.get();

        Optional<ServiceRecord> lastService = serviceRecordRepository.findByVehicleId(vehicleId)
                .stream()
                .max(Comparator.comparing(ServiceRecord::getServiceDate));

        int lastServiceMileage = lastService.map(ServiceRecord::getMileage).orElse(0);
        int currentMileage = currentMileageOverride != null ? currentMileageOverride : lastServiceMileage;

        ServiceReminder reminder = ServiceReminder.forVehicle(vehicle);
        int milesUntilService = reminder.calculateMilesUntilService(currentMileage, lastServiceMileage);
        int daysUntilService = reminder.calculateDaysUntilService(currentMileage, lastServiceMileage);

        Map<String, Object> result = new HashMap<>();
        result.put("vehicleId", vehicle.getId());
        result.put("vehicleName", vehicle.getName());
        result.put("vehicleType", vehicle.getType());
        result.put("hasServiceHistory", lastService.isPresent());
        result.put("lastServiceMileage", lastServiceMileage);
        result.put("currentMileage", currentMileage);
        result.put("serviceIntervalMiles", reminder.getServiceIntervalMiles());
        result.put("milesUntilService", milesUntilService);
        result.put("daysUntilService", daysUntilService);
        result.put("nextServiceMileage", lastServiceMileage + reminder.getServiceIntervalMiles());
        return result;
    }
}
