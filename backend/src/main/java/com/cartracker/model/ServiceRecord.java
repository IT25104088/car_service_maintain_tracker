package com.cartracker.model;

import java.time.LocalDate;

/**
 * ServiceRecord class represents a maintenance service performed on a vehicle.
 * Encapsulation: All fields are private with getters and setters.
 */
public class ServiceRecord {
    private String id;
    private String vehicleId;
    private String vehicleName;
    private String serviceType;
    private LocalDate serviceDate;
    private double cost;
    private int mileage;
    private String mechanic;
    private String notes;

    // Constructor
    public ServiceRecord() {
    }

    public ServiceRecord(String id, String vehicleId, String vehicleName, String serviceType,
                        LocalDate serviceDate, double cost, int mileage, String mechanic, String notes) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.vehicleName = vehicleName;
        this.serviceType = serviceType;
        this.serviceDate = serviceDate;
        this.cost = cost;
        this.mileage = mileage;
        this.mechanic = mechanic;
        this.notes = notes;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDate serviceDate) {
        this.serviceDate = serviceDate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getMechanic() {
        return mechanic;
    }

    public void setMechanic(String mechanic) {
        this.mechanic = mechanic;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return id + "|" + vehicleId + "|" + vehicleName + "|" + serviceType + "|" +
               serviceDate + "|" + cost + "|" + mileage + "|" + mechanic + "|" + notes;
    }
}
