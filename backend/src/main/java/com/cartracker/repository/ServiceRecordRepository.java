package com.cartracker.repository;

import com.cartracker.model.ServiceRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ServiceRecordRepository manages persistence of service records to/from text files.
 */
@Repository
public class ServiceRecordRepository extends FileBasedRepository<ServiceRecord> {

    public ServiceRecordRepository(@Value("${app.data.services.path:data/services.txt}") String filePath) {
        super(filePath);
    }

    @Override
    protected String getHeader() {
        return "ID|VehicleID|VehicleName|ServiceType|ServiceDate|Cost|Mileage|Mechanic|Notes";
    }

    @Override
    protected ServiceRecord parseRecord(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length < 9) return null;

            ServiceRecord record = new ServiceRecord();
            record.setId(parts[0]);
            record.setVehicleId(parts[1]);
            record.setVehicleName(parts[2]);
            record.setServiceType(parts[3]);
            record.setServiceDate(LocalDate.parse(parts[4]));
            record.setCost(Double.parseDouble(parts[5]));
            record.setMileage(Integer.parseInt(parts[6]));
            record.setMechanic(parts[7]);
            record.setNotes(parts[8]);

            return record;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected String recordToString(ServiceRecord record) {
        return record.toString();
    }

    public Optional<ServiceRecord> findById(String id) {
        return findAll().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();
    }

    public List<ServiceRecord> findByVehicleId(String vehicleId) {
        return findAll().stream()
                .filter(r -> r.getVehicleId().equals(vehicleId))
                .sorted(Comparator.comparing(ServiceRecord::getServiceDate).reversed())
                .collect(Collectors.toList());
    }

    public List<ServiceRecord> findByVehicleIdAndServiceType(String vehicleId, String serviceType) {
        return findByVehicleId(vehicleId).stream()
                .filter(r -> r.getServiceType().equalsIgnoreCase(serviceType))
                .collect(Collectors.toList());
    }

    public synchronized void update(ServiceRecord record) {
        List<ServiceRecord> records = findAll();
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getId().equals(record.getId())) {
                records.set(i, record);
                saveAll(records);
                return;
            }
        }
    }

    public synchronized void deleteById(String id) {
        List<ServiceRecord> records = findAll();
        records.removeIf(r -> r.getId().equals(id));
        saveAll(records);
    }
}
