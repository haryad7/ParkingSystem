package models;

import enums.SpotSize;
import enums.SpotStatus;
import java.util.ArrayList;
import enums.VehicleType;

/**
 * Represents the entire parking lot with multiple parking spots Manages spot
 * allocation and availability
 *
 * @author haryad
 */
public class ParkingLot {

    private String name;
    private String address;
    private ArrayList<ParkingSpot> spots;
    private int totalCapacity;

    private static int parkingLotCount = 0;

    /**
     * Constructor for ParkingLot
     *
     * @param name the name of the parking lot
     * @param address the address/location
     * @param totalCapacity total number of parking spots
     */
    public ParkingLot(String name, String address, int totalCapacity) {
        this.name = name;
        this.address = address;
        this.totalCapacity = totalCapacity;
        this.spots = new ArrayList<>();
        initializeSpots();
        parkingLotCount++;
    }

    /**
     * Overloaded constructor without address
     *
     * @param name the name of the parking lot
     * @param totalCapacity total number of parking spots
     */
    public ParkingLot(String name, int totalCapacity) {
        this(name, "Unknown address", totalCapacity);
    }

    /**
     * Initialize parking spots with distribution: 40% COMPACT, 40% REGULAR, 20%
     * LARGE
     */
    private void initializeSpots() {
        int compactCount = (int) (totalCapacity * 0.4);
        int regularCount = (int) (totalCapacity * 0.4);
        int largeCount = totalCapacity - compactCount - regularCount;

        int spotNumber = 1;

        spotNumber = addSpots(spotNumber, compactCount, SpotSize.COMPACT);
        spotNumber = addSpots(spotNumber, regularCount, SpotSize.REGULAR);
        addSpots(spotNumber, largeCount, SpotSize.LARGE);
    }

    /**
     * Populates the parking lot with a specific number of spots of a given
     * size. This method increments the spot numbering sequentially.
     *
     * @param startNumber The starting ID/number for the first spot created.
     * @param count The total number of spots to be added to the list.
     * @param size The {@link SpotSize} category (e.g., COMPACT, LARGE).
     * @return The next available spot number after the last spot is created.
     */
    private int addSpots(int startNumber, int count, SpotSize size) {
        for (int i = 0; i < count; i++) {
            spots.add(new ParkingSpot(startNumber++, size));
        }
        return startNumber;
    }

    /**
     * Get count of available spots
     *
     * @return number of available spots
     */
    public int getAvailableSpotsCount() {
        int count = 0;

        for (ParkingSpot spot : spots) {
            if (spot.isAvailable()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Get count of occupied spots
     *
     * @return number of occupied spots
     */
    public int getOccupiedSpotsCount() {
        int count = 0;

        for (ParkingSpot spot : spots) {
            if (spot.isOccupied()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Get count of available spots by specific size. Useful for checking if
     * appropriate spots exist for vehicle type.
     *
     * @param size the spot size to count
     * @return number of available spots of that size
     */
    public int getAvailableSpotsCountBySize(SpotSize size) {
        int count = 0;

        for (ParkingSpot spot : spots) {
            if (spot.isAvailable() && spot.getSize() == size) {
                count++;
            }
        }
        return count;
    }

    /**
     * Find an available spot of specific size. Returns first available spot
     * found, or null if none available.
     *
     * @param size the required spot size
     * @return available ParkingSpot or null if none found
     */
    public ParkingSpot findAvailableSpot(SpotSize size) {
        for (ParkingSpot spot : spots) {
            if (spot.isAvailable() && spot.getSize() == size) {
                return spot;
            }
        }
        return null;
    }

    /**
     * Find ANY available spot regardless of size. Returns first available spot
     * found.
     *
     * @overload
     * @return first available ParkingSpot or null if lot is full
     */
    public ParkingSpot findAvailableSpot() {
        for (ParkingSpot spot : spots) {
            if (spot.isAvailable()) {
                return spot;
            }
        }
        return null;
    }

    /**
     * Find a spot by its spot number.
     *
     * @param spotNumber the spot number to find
     * @return ParkingSpot with that number, or null if not found
     */
    public ParkingSpot findSpotByNumber(int spotNumber) {
        for (ParkingSpot spot : spots) {
            if (spotNumber == spot.getSpotNumber()) {
                return spot;
            }
        }
        return null;
    }

    /**
     * Find the spot where a specific vehicle is parked. Searches by license
     * plate.
     *
     * @param licensePlate the vehicle's license plate
     * @return ParkingSpot containing the vehicle, or null if not found
     */
    public ParkingSpot findSpotByVehicle(String licensePlate) {
        for (ParkingSpot spot : spots) {
            if (!spot.isAvailable()) {
                Vehicle v = spot.getCurrentVehicle();
                if (v != null && v.getLicensePlate().equals(licensePlate)) {
                    return spot;
                }
            }
        }
        return null;
    }

    /**
     * Automatically determine appropriate spot size based on vehicle type.
     * Motorcycles → COMPACT, Cars → REGULAR, Trucks → LARGE
     *
     * @param vehicle the vehicle to check
     * @return recommended SpotSize
     */
    public SpotSize determineSpotSize(Vehicle vehicle) {
        VehicleType type = vehicle.getType();

        return switch (type) {
            case MOTORCYCLE ->
                SpotSize.COMPACT;
            case CAR ->
                SpotSize.REGULAR;
            case TRUCK ->
                SpotSize.LARGE;
            default ->
                null;
        };

    }
    
    
    public boolean isVehicleExist(Vehicle vehicle){
        for (ParkingSpot spot : spots) {
            if( spot.getCurrentVehicle() == vehicle){
              return true;
            }  
        }
        return false;
    }
    

    /**
     * Park a vehicle in a spot of specified size. First tries to find spot of
     * exact size, then tries larger sizes if needed.
     *
     * @param vehicle the vehicle to park
     * @param preferredSize preferred spot size
     * @return the ParkingSpot used, or null if parking failed
     */
    public ParkingSpot parkVehicle(Vehicle vehicle, SpotSize preferredSize) {
        // Try to find preferred size first
        ParkingSpot spot = findAvailableSpot(preferredSize);

        // If preferred size not available, try larger sizes
        if (spot == null && preferredSize == SpotSize.COMPACT) {
            spot = findAvailableSpot(SpotSize.REGULAR);
            if (spot == null) {
                spot = findAvailableSpot(SpotSize.LARGE);
            }
        } else if (spot == null && preferredSize == SpotSize.REGULAR) {
            spot = findAvailableSpot(SpotSize.LARGE);
        }

        // Attempt to park if spot found
        if (spot != null && !isVehicleExist(vehicle)) {
            boolean success = spot.parkVehicle(vehicle);
            if (success) {
                return spot;
            }
        }

        return null; // No suitable spot available
    }
    
    /**
     * Park a vehicle in the lot with automatic spot selection. Determines
     * appropriate spot size based on vehicle type.
     *
     * @param vehicle the vehicle to park
     * @return the ParkingSpot used, or null if parking failed
     * @overload
     */
    public ParkingSpot parkVehicle(Vehicle vehicle) {
        SpotSize requiredSize = determineSpotSize(vehicle);
        return parkVehicle(vehicle, requiredSize);
    }

    /**
     * Remove a vehicle from the lot by license plate. Searches for the vehicle
     * and removes it from its spot.
     *
     * @param licensePlate the vehicle's license plate
     * @return the removed Vehicle or null if not found
     */
    public Vehicle removeVehicle(String licensePlate) {
        ParkingSpot spot = findSpotByVehicle(licensePlate);

        if (spot != null) {
            return spot.removeVehicle();
        }

        return null;
    }

    /**
     * Get list of spots by status.
     *
     * @param status the status to filter by
     * @return ArrayList of spots with that status
     */
    public ArrayList<ParkingSpot> getSpotsByStatus(SpotStatus status) {
        ArrayList<ParkingSpot> filtered = new ArrayList<>();
        for (ParkingSpot spot : spots) {
            if (spot.getStatus() == status) {
                filtered.add(spot);
            }
        }
        return filtered;
    }

    /**
     * Display parking lot status summary. Shows capacity, availability, and
     * breakdown by size.
     */
    public void displayStatus() {
        System.out.println("========================================");
        System.out.println("Parking Lot: " + name);
        System.out.println("Address: " + address);
        System.out.println("========================================");
        System.out.println("Total Capacity: " + totalCapacity);
        System.out.println("Available Spots: " + getAvailableSpotsCount());
        System.out.println("Occupied Spots: " + getOccupiedSpotsCount());
        System.out.println("Status: " + (isFull() ? "FULL" : (isEmpty() ? "EMPTY" : "OPEN")));
        System.out.println("----------------------------------------");
        System.out.println("Available by Size:");
        System.out.println("  COMPACT: " + getAvailableSpotsCountBySize(SpotSize.COMPACT));
        System.out.println("  REGULAR: " + getAvailableSpotsCountBySize(SpotSize.REGULAR));
        System.out.println("  LARGE:   " + getAvailableSpotsCountBySize(SpotSize.LARGE));
        System.out.println("========================================");
    }

    /**
     * Check if parking lot is full
     *
     * @return true if no available spots
     */
    public boolean isFull() {
        return getAvailableSpotsCount() == 0;
    }

    /**
     * Check if parking lot is empty
     *
     * @return true if all spots available
     */
    public boolean isEmpty() {
        return getOccupiedSpotsCount() == 0;
    }

    /**
     * Display parking lot statistics.
     */
    public void displayStatistics() {
        System.out.println("\n========== Parking Statistics ==========");
        System.out.println("Lot Name: " + name);
        System.out.println("Total Spots: " + totalCapacity);
        System.out.println("Occupied: " + getOccupiedSpotsCount()
                + " (" + String.format("%.1f", (getOccupiedSpotsCount() * 100.0 / totalCapacity)) + "%)");
        System.out.println("Available: " + getAvailableSpotsCount()
                + " (" + String.format("%.1f", (getAvailableSpotsCount() * 100.0 / totalCapacity)) + "%)");

        System.out.println("\nSpot Distribution:");
        int compactTotal = 0, regularTotal = 0, largeTotal = 0;
        for (ParkingSpot spot : spots) {

            switch (spot.getSize()) {
                case COMPACT ->
                    compactTotal++;
                case REGULAR ->
                    regularTotal++;
                case LARGE ->
                    largeTotal++;
                default ->
                    largeTotal++;
            }
        }
    System.out.println (

    "  COMPACT: " + compactTotal + " total, "
                + getAvailableSpotsCountBySize(SpotSize.COMPACT) + " available");
    System.out.println (

    "  REGULAR: " + regularTotal + " total, "
                + getAvailableSpotsCountBySize(SpotSize.REGULAR) + " available");
    System.out.println (

    "  LARGE:   " + largeTotal + " total, "
                + getAvailableSpotsCountBySize(SpotSize.LARGE) + " available");
    System.out.println (

"========================================\n");
    }
    
    /**
     * Display all parking spots with details.
     * Useful for debugging and detailed status checking.
     */
    public void displayAllSpots() {
        System.out.println("\n========== All Parking Spots ==========");
        for (ParkingSpot spot : spots) {
            System.out.println(spot);
        }
        System.out.println("=======================================\n");
    }
    
    

    //setters
    /**
     * Set parking lot name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set parking lot address.
     *
     * @param address the new address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    // Note: No setTotalCapacity() - capacity is fixed at construction
    // Note: No setSpots() - spots are managed internally
    /**
     * Get total capacity. No setter provided - capacity is fixed at
     * construction (like building capacity).
     *
     * @return total capacity
     */
    public int getTotalCapacity() {
        return totalCapacity;
    }

    /**
     * Get total count of parking lots created (static method from Lecture 3).
     *
     * @return count of parking lots
     */
    public static int getParkingLotCount() {
        return parkingLotCount;
    }

    /**
     * Get parking lot address.
     *
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Get parking lot name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get all parking spots. Returns the actual list - use with caution
     * (consider defensive copy for production).
     *
     * @return ArrayList of all parking spots
     */
    public ArrayList<ParkingSpot> getSpots() {
        return spots;
    }

    @Override
public String toString() {
        return "ParkingLot [" + name + ", " + address
                + ", Capacity: " + totalCapacity
                + ", Available: " + getAvailableSpotsCount()
                + ", Occupied: " + getOccupiedSpotsCount() + "]";
    }

}
