package models;

import enums.VehicleType;

/**
 * Represents a Motorcycle in the parking system
 * @author Shanya
 * @author Haryad
 */
public class Motorcycle extends Vehicle{
    private boolean hasSidecar;
    private static final double BASE_RATE = 1.0;
    private static final double SIDECAR_MULTIPLIER = 1.5;
    
    public Motorcycle(String licensePlate,String color,String brand,String model,int year , boolean hasSidecar ){
        super(licensePlate, VehicleType.MOTORCYCLE, color, brand, model,year);
        this.hasSidecar = hasSidecar;
    }
    @Override
    public double getParkingRate(){
        return hasSidecar? BASE_RATE*SIDECAR_MULTIPLIER : BASE_RATE;
    };
    
    @Override
    public double calculateParkingFee(int hours){
        if (hours < 0) {
            throw new IllegalArgumentException("Hours cannot be negative");
        }
       return hours* getParkingRate();
    }
    
    public boolean getHasSidecar(){
        return hasSidecar;
    }
    public void setHasSidecar(boolean hasSidecar) {
        this.hasSidecar = hasSidecar;
    }
    
    @Override
    public String toString() {
        return "Motorcycle [" + brand + " " + model + ", " + 
               licensePlate + ", " + color +", "  +year+ 
               ", Rate: $" + getParkingRate() + "/hr]";
    }
}
