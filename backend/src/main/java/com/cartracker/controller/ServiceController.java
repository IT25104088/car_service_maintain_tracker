package com.cartracker.controller;

import com.cartracker.model.ServiceRecord;
import com.cartracker.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * ServiceController provides REST API endpoints for service records.
 */
@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "*", methods = {org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.PUT, org.springframework.web.bind.annotation.RequestMethod.DELETE})
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @PostMapping
    public ResponseEntity<ServiceRecord> createService(@RequestBody ServiceRecord serviceRecord) {
        ServiceRecord created = serviceService.createService(serviceRecord);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<ServiceRecord>> getAllServices() {
        List<ServiceRecord> services = serviceService.getAllServices();
        return ResponseEntity.ok(services);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceRecord> getServiceById(@PathVariable String id) {
        Optional<ServiceRecord> service = serviceService.getServiceById(id);
        return service.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<ServiceRecord>> getServicesByVehicleId(@PathVariable String vehicleId) {
        List<ServiceRecord> services = serviceService.getServicesByVehicleId(vehicleId);
        return ResponseEntity.ok(services);
    }

    @GetMapping("/vehicle/{vehicleId}/type/{serviceType}")
    public ResponseEntity<List<ServiceRecord>> getServicesByVehicleIdAndType(
            @PathVariable String vehicleId,
            @PathVariable String serviceType) {
        List<ServiceRecord> services = serviceService.getServicesByVehicleIdAndType(vehicleId, serviceType);
        return ResponseEntity.ok(services);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceRecord> updateService(
            @PathVariable String id,
            @RequestBody ServiceRecord serviceRecord) {
        ServiceRecord updated = serviceService.updateService(id, serviceRecord);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable String id) {
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/vehicle/{vehicleId}/total-cost")
    public ResponseEntity<Double> getTotalCostByVehicleId(@PathVariable String vehicleId) {
        double totalCost = serviceService.getTotalCostByVehicleId(vehicleId);
        return ResponseEntity.ok(totalCost);
    }
}
