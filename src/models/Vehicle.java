package models;
import enums.VehicleType;

/**
 *
 * @author Shanya
 * @author Haryad
 */
public abstract class Vehicle{
    
    
    protected String licensePlate;
    protected String color;
    protected VehicleType type;
    protected String model;
    protected String brand;
    protected int year;
    private static int vehicleCount = 0;

    /**
     * Constructor for Vehicle
     *
     * @param licensePlate the license plate number
     * @param type the type of vehicle
     * @param color the color of the vehicle
     * @param brand brand of the vehicle
     * @param model model of the vehicle
     * @param year the year of the vehicle
     * @throws IllegalArgumentException if license plate is null or empty
     */
    
    public Vehicle(String licensePlate,VehicleType type ,String color,String brand,String model,int year) {
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be null or empty");
        }
        
        this.licensePlate = licensePlate.trim().toUpperCase();
        this.color = color;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.year = year;
        vehicleCount++;
    }

    /**
      * @return the hourly parking rate for this vehicle type
     */
    
    public abstract double getParkingRate();
    
    /**
     * Abstract method for calculating parking fee
     * @param hours number of hours parked
     * @return total parking fee
     */
    
    public abstract double calculateParkingFee(int hours);
    
     /**
     * Get the license plate
     * @return license plate string
     */
    
    public String getLicensePlate() {
        return licensePlate;
    }
    
    /**
     * Get the vehicle type
     * @return VehicleType enum
     */
    public VehicleType getType() {
        return type;
    }
    
    /**
     * Get the vehicle color
     * @return color string
     */
    public String getColor() {
        return color;
    }
    
    /**
     * Set the vehicle color
     * @param color the new color
     */
    public void setColor(String color) {
        this.color = color;
    }
    
    /**
     * @return total number of vehicles created
    **/ 
     
    public static int getVehicleCount() {
        return vehicleCount;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    /**
     * Override equals() to compare vehicles by license plate
     * @param obj object to compare
     * @return true if same license plate
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vehicle vehicle = (Vehicle) obj;
        return licensePlate.equals(vehicle.licensePlate);
    }
    
    /**
     * Override hashCode() for proper use in collections
     * @return hash code based on license plate
     */
    @Override
    public int hashCode() {
        return licensePlate.hashCode();
    }
    
    /**
     * @return string description
     */
//    @Override
//    public String toString() {
//        return type + " [" + brand + " " + model + ", " + 
//               licensePlate + ", " + color +", "  +year+ "]";
//    }
 }
