
package models;
import enums.VehicleType;

/**
 *
 * @author Haryad
 */
public class Truck extends Vehicle{
    private double weightInTon;
    private static final double BASE_RATE = 3.0;
    private static final double WEIGHT_SURCHARGE = 5.0;
    
    public Truck(String licensePlate,String color,String brand,String model,int year, double weightInTon){
        super(licensePlate, VehicleType.TRUCK, color, brand, model,year);
        this.weightInTon = weightInTon;
    }
    @Override
    public double getParkingRate(){
        double rate = BASE_RATE;
        if(weightInTon > 2.0){
            rate += (weightInTon - 2.0)* WEIGHT_SURCHARGE;
        }
        return rate;
    }
    
    @Override
    public double calculateParkingFee(int hours){
        return hours * getParkingRate();
    }
    
    public double getWeightInTon(){
        return weightInTon;
    }
    
    @Override
    public String toString() {
        return   "Truck [" + brand + " " + model + ", " + 
               licensePlate + ", " + color +", "  +year+", "  +
                weightInTon +" tons, Rate: $"+ getParkingRate() + "/hr]";
    }
    
    
}
