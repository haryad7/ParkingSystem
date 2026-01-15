
package interfaces;

import models.ParkingSpot;

/**
 * Interface defining the contract for objects that can be parked.
 * Any vehicle that can occupy a parking spot must implement this interface.
 * 
 * @author Parking System Team
 * @version 1.0
 */
public interface Parkable {
    /**
     * Parks the vehicle in the specified parking spot.
     * 
     * @param spot The parking spot where the vehicle will be parked
     * @return true if parking was successful, false otherwise
     */
    boolean park(ParkingSpot spot);
    
    /**
     * Removes the vehicle from its current parking spot.
     * 
     * @return true if the vehicle was successfully unparked, false otherwise
     */
    boolean unpark();
    
    /**
     * Checks if the vehicle is currently parked.
     * 
     * @return true if the vehicle is parked, false otherwise
     */
    boolean isParked();
}