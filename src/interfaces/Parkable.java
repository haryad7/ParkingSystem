package interfaces;

import models.Vehicle;
import models.ParkingSpot;
import enums.VehicleType;
import enums.SpotSize;

/**
 * Interface for entities that can manage parking operations
 * Implemented by classes that handle vehicle parking and removal
 * @author Paywast
 * @author Haryad
 */
public interface Parkable {
    
    /**
     * Park a vehicle in the parking system
     * @param vehicle the vehicle to be parked
     * @return the ParkingSpot where vehicle was parked, or null if parking failed
     */
    ParkingSpot park(Vehicle vehicle);
    
    /**
     * Remove a vehicle from the parking system
     * @param licensePlate the license plate of the vehicle to remove
     * @return the Vehicle that was removed, or null if not found
     */
    Vehicle unpark(String licensePlate);
    
    /**
     * Find where a vehicle is currently parked
     * @param licensePlate the license plate of the vehicle
     * @return the ParkingSpot where vehicle is parked, or null if not found
     */
    ParkingSpot findVehicleLocation(String licensePlate);
    
    /**
     * Check if there is space available for parking
     * @return true if parking space is available, false otherwise
     */
    boolean hasAvailableSpace();
    
    /**
     * Get the total number of vehicles currently parked
     * @return count of parked vehicles
     */
    int getCurrentOccupancy();
    
    /**
     * Check if a specific vehicle is currently parked
     * @param licensePlate the license plate to check
     * @return true if vehicle is parked, false otherwise
     */
    default boolean isVehicleParked(String licensePlate) {
        return findVehicleLocation(licensePlate) != null;
    }
    
    /**
     * Calculate occupancy rate as a percentage
     * @return occupancy percentage (0.0 to 100.0)
     */
    default double getOccupancyRate() {
        int current = getCurrentOccupancy();
        // This would need total capacity - implementing classes should override
        return current > 0 ? 100.0 : 0.0;
    }
    
    /**
     * Check if the parking facility is full
     * @return true if no available spaces
     */
    default boolean isFull() {
        return !hasAvailableSpace();
    }
    
    /**
     * Check if the parking facility is empty
     * @return true if no vehicles parked
     */
    default boolean isEmpty() {
        return getCurrentOccupancy() == 0;
    }
}