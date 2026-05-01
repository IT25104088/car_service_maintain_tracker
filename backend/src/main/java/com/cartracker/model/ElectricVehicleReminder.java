package com.cartracker.model;

public class ElectricVehicleReminder extends ServiceReminder {
    private static final int SERVICE_INTERVAL_MILES = 10000;

    public ElectricVehicleReminder(Vehicle vehicle) {
        super(vehicle);
    }

    @Override
    public int getServiceIntervalMiles() {
        return SERVICE_INTERVAL_MILES;
    }
}
