package models;

/**
 * Represents a parking ticket issued to a vehicle
 * Tracks entry time, exit time, and parking fee
 * Uses simple long values for timestamps instead of LocalDateTime
 * @author Haryad
 */
public class Ticket {
    
    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSpot parkingSpot;
    private final long entryTime;  // Milliseconds since epoch
    private long exitTime;         // 0 means still active
    private double parkingFee;
    private boolean isPaid;
    
    private static int ticketCounter = 0;
    
    /**
     * Constructor for Ticket
     * @param vehicle the vehicle being parked
     * @param parkingSpot the spot where vehicle is parked
     */
    public Ticket(Vehicle vehicle, ParkingSpot parkingSpot) {
        this.ticketId = generateTicketId();
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        this.entryTime = System.currentTimeMillis();
        this.exitTime = 0;
        this.parkingFee = 0.0;
        this.isPaid = false;
        ticketCounter++;
    }
    
    /**
     * Generate a unique ticket ID
     * @return unique ticket ID string
     */
    private String generateTicketId() {
        return "TKT-" + String.format("%06d", ticketCounter + 1);
    }
    
    /**
     * Calculate parking duration in hours (rounded up)
     * @return hours parked
     */
    public int calculateHoursParked() {
        long endTime = (exitTime != 0) ? exitTime : System.currentTimeMillis();
        long durationMillis = endTime - entryTime;
        
        // Convert milliseconds to hours and round up
        long hours = durationMillis / (1000 * 60 * 60);
        long remainder = durationMillis % (1000 * 60 * 60);
        
        // If there's any remainder, add 1 hour (round up)
        if (remainder > 0) {
            hours++;
        }
        
        return (int) hours;
    }
    
    /**
     * Calculate the parking fee based on hours parked
     * @return calculated fee
     */
    public double calculateFee() {
        int hours = calculateHoursParked();
        this.parkingFee = vehicle.calculateParkingFee(hours);
        return parkingFee;
    }
    
    /**
     * Mark the ticket as paid
     * @return true if successfully marked as paid
     */
    public boolean markAsPaid() {
        if (!isPaid) {
            this.isPaid = true;
            return true;
        }
        return false;
    }
    
    /**
     * Complete the ticket by setting exit time
     */
    public void completeTicket() {
        if (exitTime == 0) {
            this.exitTime = System.currentTimeMillis();
            calculateFee();
        }
    }
    
    /**
     * Check if ticket is active (not completed)
     * @return true if ticket is still active
     */
    public boolean isActive() {
        return exitTime == 0;
    }
    
    /**
     * Format timestamp as readable string
     * @param timeMillis timestamp in milliseconds
     * @return formatted string
     */
    private String formatTime(long timeMillis) {
        // Simple date formatting using Java's built-in Date class
        java.util.Date date = new java.util.Date(timeMillis);
        return date.toString();
    }
    
    // Getters
    public String getTicketId() {
        return ticketId;
    }
    
    public Vehicle getVehicle() {
        return vehicle;
    }
    
    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }
    
    public long getEntryTime() {
        return entryTime;
    }
    
    public long getExitTime() {
        return exitTime;
    }
    
    public double getParkingFee() {
        return parkingFee;
    }
    
    public boolean isPaid() {
        return isPaid;
    }
    
    public static int getTicketCounter() {
        return ticketCounter;
    }
    
    /**
     * Display ticket information
     */
    public void displayTicket() {
        System.out.println("\n========== PARKING TICKET ==========");
        System.out.println("Ticket ID: " + ticketId);
        System.out.println("License Plate: " + vehicle.getLicensePlate());
        System.out.println("Vehicle: " + vehicle.getBrand() + " " + vehicle.getModel());
        System.out.println("Vehicle Type: " + vehicle.getType());
        System.out.println("Parking Spot: #" + parkingSpot.getSpotNumber() + 
                         " (" + parkingSpot.getSize() + ")");
        System.out.println("Entry Time: " + formatTime(entryTime));
        
        if (exitTime != 0) {
            System.out.println("Exit Time: " + formatTime(exitTime));
            System.out.println("Hours Parked: " + calculateHoursParked());
            System.out.println("Parking Fee: $" + String.format("%.2f", parkingFee));
            System.out.println("Payment Status: " + (isPaid ? "PAID" : "UNPAID"));
        } else {
            System.out.println("Status: ACTIVE");
            System.out.println("Current Hours: " + calculateHoursParked());
            System.out.println("Current Fee: $" + String.format("%.2f", calculateFee()));
        }
        
        System.out.println("====================================\n");
    }
    
    @Override
    public String toString() {
        return "Ticket [" + ticketId + ", " + vehicle.getLicensePlate() + 
               ", Spot #" + parkingSpot.getSpotNumber() + 
               ", " + (isActive() ? "ACTIVE" : "COMPLETED") + 
               ", Fee: $" + String.format("%.2f", parkingFee) + "]";
    }
}