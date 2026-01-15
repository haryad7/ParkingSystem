package models;

import enums.SpotSize;
import enums.SpotStatus;
import enums.VehicleType;

/**
 * Represents a single parking spot in the parking lot Each spot has a size,
 * status, and can hold one vehicle
 * <p><b>Design Notes:</b></p>
 * <ul>
 * <li>No setter for spotNumber - spot numbers are permanent identifiers</li>
 * <li>No setter for size - physical size of a spot cannot change after construction</li>
 * <li>No setter for currentVehicle - use parkVehicle() and removeVehicle() methods instead
 *     to maintain consistency between vehicle and status</li>
 * <li>setStatus() is provided but controlled operations (parkVehicle, reserve, etc.) 
 *     are preferred to maintain data integrity</li>
 * </ul>
 * @author Haryad
 * @param <v>
 */
public class ParkingSpot<v extends Vehicle> {

    private int spotNumber;
    private SpotSize size;
    private SpotStatus status;
    private v currentVehicle;

    private static int totalSpotsCreated = 0;

    /**
     * Constructor for ParkingSpot
     *
     * @param spotNumber the unique spot number
     * @param size the size of the spot (COMPACT, REGULAR, LARGE)
     *
     */
    public ParkingSpot(int spotNumber, SpotSize size) {
        this.spotNumber = spotNumber;
        this.size = size;
        this.status = SpotStatus.AVAILABLE;
        this.currentVehicle = null;
        totalSpotsCreated++;
    }

    /**
     * Park a vehicle in this spot
     *
     * @param vehicle the vehicle to park
     * @return true if successfully parked, false if spot is occupied
     */
    public boolean parkVehicle(v vehicle) {

        if (status == SpotStatus.AVAILABLE) {
            this.currentVehicle = vehicle;
            this.status = SpotStatus.OCCUPIED;
            return true;
        }
        return false;
    }

    /**
     * Remove the vehicle from this spot
     *
     * @return the vehicle that was removed, or null if spot was empty
     */
    public Vehicle removeVehicle() {
        if (currentVehicle != null) {
            Vehicle removedVehicle = this.currentVehicle;
            this.currentVehicle = null;
            this.status = SpotStatus.AVAILABLE;
            return removedVehicle;
        }
        return null;
    }

    /**
     * Check if spot is available
     *
     * @return true if status
     */
    public boolean isAvailable() {
        return status == SpotStatus.AVAILABLE;
    }

    /**
     * Check if spot is occupied
     *
     * @return true if status is OCCUPIED
     */
    public boolean isOccupied() {
        return status == SpotStatus.OCCUPIED;
    }

    /**
     * Check if vehicle fits in this spot based on size
     *
     * @param vehicle the vehicle to check
     * @return true if vehicle can fit
     */
    public boolean canFit(Vehicle vehicle) {
        // Logic: 
        // COMPACT spots: only motorcycles
        // REGULAR spots: motorcycles and cars
        // LARGE spots: all vehicles (motorcycles, cars, trucks)

        return switch (size) {
            case COMPACT ->
                vehicle.getType() == VehicleType.MOTORCYCLE;
            case REGULAR ->
                vehicle.getType() == VehicleType.MOTORCYCLE || vehicle.getType() == VehicleType.CAR;
            case LARGE ->
                true;
            default ->
                false;
        };

    }

    /**
     * Reserve the spot (mark as reserved but no vehicle yet)
     *
     * @return true if successfully reserved
     */
    public boolean reserve() {
        if (status == SpotStatus.AVAILABLE) {
            this.status = SpotStatus.RESERVED;
            return true;
        }
        return false;
    }

    /**
     * Cancel reservation (make available again)
     *
     * @return true if successfully cancelled
     */
    public boolean cancelReservation() {
        if (status == SpotStatus.RESERVED) {
            this.status = SpotStatus.AVAILABLE;
            return true;
        }
        return false;
    }

    /**
     * Mark spot as out of service
     */
    public void markOutOfService() {
        this.status = SpotStatus.OUT_OF_SERVICE;
        this.currentVehicle = null;
    }

    /**
     * Return spot to service (make available)
     */
    public void returnToService() {
        if (status == SpotStatus.OUT_OF_SERVICE) {
            this.status = SpotStatus.AVAILABLE;
        }
    }
    
    // Getters and setters
    public int getSpotNumber() {
        return spotNumber;
    }

    public SpotSize getSize() {
        return size;
    }


    public SpotStatus getStatus() {
        return status;
    }

    public void setStatus(SpotStatus status) {
        this.status = status;
    }

    public Vehicle getCurrentVehicle() {
        return currentVehicle;
    }


    public static int getTotalSpotsCreated() {
        return totalSpotsCreated;
    }

    

    /**
     * String representation of parking spot
     *
     * @return formatted string with spot details
     */
    public String toString() {
        String vehicleInfo = switch (status) {
            case OCCUPIED ->
                (currentVehicle != null)
                ? currentVehicle.getBrand() + " " + currentVehicle.getModel()
                + " (" + currentVehicle.getLicensePlate() + ")"
                : "Occupied (No Details)";
            case RESERVED ->
                "Reserved";
            case OUT_OF_SERVICE ->
                "out of service";
            case AVAILABLE ->
                "Empty";
            default ->
                "Unknown";
        };

        return "Spot #" + spotNumber + " [" + size + "] - " + status + " [" + vehicleInfo + "]";
    }
    
    /**
     * Detailed string with all information
     * @return detailed formatted string
     */
    public String toDetailedString(){
        StringBuilder sb = new StringBuilder();
        sb.append("=================================\n");
        sb.append("parking spot #").append(spotNumber).append("\n");
        sb.append("size: ").append(size).append("\n");
        sb.append("status: ").append(status).append("\n");
        
        if (currentVehicle != null) {
            sb.append("Current Vehicle:\n");
            sb.append("  ").append(currentVehicle.toString()).append("\n");
            sb.append("  Parking Rate: $").append(currentVehicle.getParkingRate()).append("/hr\n");
        } else {
            sb.append("Current Vehicle: None\n");
        }
        
        sb.append("=================================");
        return sb.toString();
    }

}
