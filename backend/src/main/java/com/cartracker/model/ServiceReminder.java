package com.cartracker.model;

/**
 * ServiceReminder class uses Polymorphism to calculate service reminders
 * based on vehicle type (GASOLINE or ELECTRIC).
 * Abstraction: Different reminder logic for different vehicle types.
 */
public abstract class ServiceReminder {
    protected Vehicle vehicle;

    public ServiceReminder(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * Abstract method to calculate days until next service
     * Polymorphism: Different implementations for different vehicle types
     */
    public abstract int calculateDaysUntilService(int currentMileage, int lastServiceMileage);

    public Vehicle getVehicle() {
        return vehicle;
    }
}

class GasolineVehicleReminder extends ServiceReminder {
    private static final int SERVICE_INTERVAL_MILES = 5000;

    public GasolineVehicleReminder(Vehicle vehicle) {
        super(vehicle);
    }

    @Override
    public int calculateDaysUntilService(int currentMileage, int lastServiceMileage) {
        int mileageDifference = currentMileage - lastServiceMileage;
        int remainingMiles = SERVICE_INTERVAL_MILES - mileageDifference;
        // Assuming average driving of 50 miles per day
        return Math.max(0, remainingMiles / 50);
    }
}

class ElectricVehicleReminder extends ServiceReminder {
    private static final int SERVICE_INTERVAL_MILES = 10000;

    public ElectricVehicleReminder(Vehicle vehicle) {
        super(vehicle);
    }

    @Override
    public int calculateDaysUntilService(int currentMileage, int lastServiceMileage) {
        int mileageDifference = currentMileage - lastServiceMileage;
        int remainingMiles = SERVICE_INTERVAL_MILES - mileageDifference;
        // Assuming average driving of 50 miles per day
        return Math.max(0, remainingMiles / 50);
    }
}
