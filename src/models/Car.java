
package models;

import enums.VehicleType;

/**
 * Represents a Car in the parking system
 * Extends Vehicle class demonstrating inheritance
 * @author Shanya
 * @author Haryad
 */
public class Car extends Vehicle {
    
    private static final double HOURLY_RATE = 2.0;
    
    
    /**
     * Constructor for Car
     * @param licensePlate the license plate
     * @param color the car color
     * @param brand the car brand
     * @param model model of the car
     */
    
    public Car(String licensePlate,String color, String brand, String model,int year ) {
        super(licensePlate, VehicleType.CAR, color, brand, model,year); 
        
    }
    
    @Override
    public double getParkingRate(){
        return HOURLY_RATE;
    }
    
    
    @Override
    public double calculateParkingFee(int hours){
        if (hours < 0) {
            throw new IllegalArgumentException("Hours cannot be negative");
        }
        return hours * HOURLY_RATE;
    }

    @Override
    public String toString() {
        return "Car [" + brand + " " + model + ", " + 
               licensePlate + ", " + color +", "  +year+ 
               ", Rate: $" + getParkingRate() + "/hr]";
    }
    
}
