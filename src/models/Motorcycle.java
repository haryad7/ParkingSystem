package models;

import enums.VehicleType;

/**
 * Represents a Motorcycle in the parking system
 * @author Haryad
 */
public class Motorcycle extends Vehicle{
    private boolean hasSidecar;
    private static final double HOURLY_RATE = 1.0;
    
    public Motorcycle(String licensePlate,String color,String brand,String model,int year , boolean hasSidecar ){
        super(licensePlate, VehicleType.CAR, color, brand, model,year);
        this.hasSidecar = hasSidecar;
    }
    @Override
    public double getParkingRate(){
        return hasSidecar? HOURLY_RATE*1.5 : HOURLY_RATE;
    };
    
    @Override
    public double calculateParkingFee(int hours){
       return hours* getParkingRate();
    }
    
    public boolean getHasSidecar(){
        return hasSidecar;
    }
    
    public String toString() {
        return "Motorcycle [" + brand + " " + model + ", " + 
               licensePlate + ", " + color +", "  +year+ 
               ", Rate: $" + getParkingRate() + "/hr]";
    }
}
