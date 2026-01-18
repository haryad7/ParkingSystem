
package models;
import enums.VehicleType;

/**
 *
 * @author Shanya
 * @author Haryad
 */
public class Truck extends Vehicle{
    private double weightInTon;
    private static final double BASE_RATE = 3.0;
    private static final double WEIGHT_THRESHOLD = 2.0;
    private static final double WEIGHT_SURCHARGE = 5.0;
    
    public Truck(String licensePlate,String color,String brand,String model,int year, double weightInTon){
        super(licensePlate, VehicleType.TRUCK, color, brand, model,year);
        if (weightInTon <= 0) {
            throw new IllegalArgumentException("Weight must be positive");
        }
        if (weightInTon > 50) { 
            throw new IllegalArgumentException("Weight exceeds maximum allowed (50 tons)");
        }
        this.weightInTon = weightInTon;
    }
     @Override
    public double getParkingRate() {
        double rate = BASE_RATE;
        if (weightInTon > WEIGHT_THRESHOLD) {
            rate += (weightInTon - WEIGHT_THRESHOLD) * WEIGHT_SURCHARGE;
        }
        return rate;
    }
    
    @Override
    public double calculateParkingFee(int hours){
        if (hours < 0) {
            throw new IllegalArgumentException("Hours cannot be negative");
        }
        return hours * getParkingRate();
    }
    
    public double getWeightInTon(){
        return weightInTon;
    }
    
    public void setWeightInTon(double weightInTon) {
        if (weightInTon <= 0 || weightInTon > 50) {
            throw new IllegalArgumentException("Invalid weight");
        }
        this.weightInTon = weightInTon;
    }
    
    @Override
    public String toString() {
        return   "Truck [" + brand + " " + model + ", " + 
               licensePlate + ", " + color +", "  +year+", "  +
                weightInTon +" tons, Rate: $"+ getParkingRate() + "/hr]";
    }
    
    
}
