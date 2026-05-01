package com.cartracker.service;

import com.cartracker.model.ServiceRecord;
import com.cartracker.repository.ServiceRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * ServiceService provides business logic for service records.
 */
@Service
public class ServiceService {

    @Autowired
    private ServiceRecordRepository serviceRecordRepository;

    public ServiceRecord createService(ServiceRecord serviceRecord) {
        String id = UUID.randomUUID().toString();
        serviceRecord.setId(id);
        serviceRecordRepository.save(serviceRecord);
        return serviceRecord;
    }

    public List<ServiceRecord> getAllServices() {
        return serviceRecordRepository.findAll();
    }

    public Optional<ServiceRecord> getServiceById(String id) {
        return serviceRecordRepository.findById(id);
    }

    public List<ServiceRecord> getServicesByVehicleId(String vehicleId) {
        return serviceRecordRepository.findByVehicleId(vehicleId);
    }

    public List<ServiceRecord> getServicesByVehicleIdAndType(String vehicleId, String serviceType) {
        return serviceRecordRepository.findByVehicleIdAndServiceType(vehicleId, serviceType);
    }

    public ServiceRecord updateService(String id, ServiceRecord serviceRecord) {
        Optional<ServiceRecord> existingService = serviceRecordRepository.findById(id);
        if (existingService.isPresent()) {
            serviceRecord.setId(id);
            serviceRecordRepository.update(serviceRecord);
            return serviceRecord;
        }
        throw new RuntimeException("Service record not found with id: " + id);
    }

    public void deleteService(String id) {
        serviceRecordRepository.deleteById(id);
    }

    public double getTotalCostByVehicleId(String vehicleId) {
        return serviceRecordRepository.findByVehicleId(vehicleId)
                .stream()
                .mapToDouble(ServiceRecord::getCost)
                .sum();
    }
}
