package services;

import enums.SpotSize;
import interfaces.Parkable;
import models.ParkingLot;
import models.ParkingSpot;
import models.Vehicle;

/**
 * Service class for managing parking operations
 * Implements the Parkable interface
 * Acts as a facade for ParkingLot operations
 * @author Helen
 * @author Haryad
 */
public class ParkingManager implements Parkable {
    
    private ParkingLot parkingLot;
    
    /**
     * Constructor
     * @param parkingLot the parking lot to manage
     */
    public ParkingManager(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }
    
    @Override
    public ParkingSpot park(Vehicle vehicle) {
        if (vehicle == null) {
            System.out.println("ERROR: Cannot park null vehicle");
            return null;
        }
        
        if (!hasAvailableSpace()) {
            System.out.println("ERROR: Parking lot is FULL");
            return null;
        }
        
        // Check if vehicle is already parked
        if (parkingLot.findSpotByVehicle(vehicle.getLicensePlate()) != null) {
            System.out.println("ERROR: Vehicle " + vehicle.getLicensePlate() + " is already parked");
            return null;
        }
        
        ParkingSpot spot = parkingLot.parkVehicle(vehicle);
        
        if (spot != null) {
            System.out.println("SUCCESS: " + vehicle.getType() + " (" + vehicle.getLicensePlate() + 
                             ") parked in spot #" + spot.getSpotNumber());
        } else {
            System.out.println("FAILED: Unable to park " + vehicle.getLicensePlate());
        }
        
        return spot;
    }
    
    @Override
    public Vehicle unpark(String licensePlate) {
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            System.out.println("ERROR: Invalid license plate");
            return null;
        }
        
        Vehicle vehicle = parkingLot.removeVehicle(licensePlate);
        
        if (vehicle != null) {
            System.out.println("SUCCESS: Vehicle " + licensePlate + " removed from parking lot");
        } else {
            System.out.println("ERROR: Vehicle " + licensePlate + " not found in parking lot");
        }
        
        return vehicle;
    }
    
    @Override
    public ParkingSpot findVehicleLocation(String licensePlate) {
        return parkingLot.findSpotByVehicle(licensePlate);
    }
    
    @Override
    public boolean hasAvailableSpace() {
        return parkingLot.getAvailableSpotsCount() > 0;
    }
    
    @Override
    public int getCurrentOccupancy() {
        return parkingLot.getOccupiedSpotsCount();
    }
    
    /**
     * Display comprehensive parking lot status
     */
    public void displayStatus() {
        parkingLot.displayStatus();
    }
    
    /**
     * Display detailed statistics
     */
    public void displayStatistics() {
        parkingLot.displayStatistics();
    }
    
    /**
     * Get the managed parking lot
     * @return the ParkingLot instance
     */
    public ParkingLot getParkingLot() {
        return parkingLot;
    }
    
    /**
     * Override to calculate accurate occupancy rate
     * @return occupancy percentage
     */
    @Override
    public double getOccupancyRate() {
        int total = parkingLot.getTotalCapacity();
        int occupied = getCurrentOccupancy();
        return total > 0 ? (occupied * 100.0 / total) : 0.0;
    }
    
    /**
     * Check if lot is full (override default)
     * @return true if full
     */
    @Override
    public boolean isFull() {
        return parkingLot.isFull();
    }
    
    /**
     * Check if lot is empty (override default)
     * @return true if empty
     */
    @Override
    public boolean isEmpty() {
        return parkingLot.isEmpty();
    }
    
    /**
     * Get available space count by vehicle type
     * @param vehicle the vehicle to check space for
     * @return number of suitable spots available
     */
    public int getAvailableSpaceForVehicle(Vehicle vehicle) {
        SpotSize requiredSize = parkingLot.determineSpotSize(vehicle);
        return parkingLot.getAvailableSpotsCountBySize(requiredSize);
    }
}