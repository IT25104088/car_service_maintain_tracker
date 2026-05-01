package com.cartracker.model;

/**
 * ServiceReminder uses Abstraction + Polymorphism: subclasses provide
 * vehicle-type-specific service interval logic.
 */
public abstract class ServiceReminder {
    protected Vehicle vehicle;

    public ServiceReminder(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public abstract int getServiceIntervalMiles();

    public int calculateMilesUntilService(int currentMileage, int lastServiceMileage) {
        int driven = Math.max(0, currentMileage - lastServiceMileage);
        return Math.max(0, getServiceIntervalMiles() - driven);
    }

    public int calculateDaysUntilService(int currentMileage, int lastServiceMileage) {
        // Assume average 50 miles/day
        return calculateMilesUntilService(currentMileage, lastServiceMileage) / 50;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Factory: pick the reminder implementation for the vehicle's type.
     */
    public static ServiceReminder forVehicle(Vehicle vehicle) {
        if (vehicle == null || vehicle.getType() == null) {
            return new GasolineVehicleReminder(vehicle);
        }
        return switch (vehicle.getType().toUpperCase()) {
            case "ELECTRIC" -> new ElectricVehicleReminder(vehicle);
            default -> new GasolineVehicleReminder(vehicle);
        };
    }
}
