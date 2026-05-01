package com.cartracker.model;

public class GasolineVehicleReminder extends ServiceReminder {
    private static final int SERVICE_INTERVAL_MILES = 5000;

    public GasolineVehicleReminder(Vehicle vehicle) {
        super(vehicle);
    }

    @Override
    public int getServiceIntervalMiles() {
        return SERVICE_INTERVAL_MILES;
    }
}
