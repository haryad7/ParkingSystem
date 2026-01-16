package services;

import models.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Central service class managing all parking operations.
 * Coordinates between vehicles, spots, tickets, and payments.
 * 
 * @author Parking System Team
 * @version 1.0
 */
public class ParkingManager {
    /** The parking lot being managed */
    private ParkingLot parkingLot;
    
    /** Ticket generator service */
    private TicketGenerator ticketGenerator;
    
    /** Active tickets mapped by ticket number */
    private Map<String, Ticket> activeTickets;
    
    /**
     * Constructs a new ParkingManager for the specified parking lot.
     * 
     * @param parkingLot The parking lot to manage
     */
    public ParkingManager(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
        this.ticketGenerator = new TicketGenerator();
        this.activeTickets = new HashMap<>();
    }
    
    /**
     * Parks a vehicle in the parking lot.
     * Finds an available spot, parks the vehicle, and issues a ticket.
     * 
     * @param vehicle The vehicle to park
     * @return The issued ticket, or null if parking failed
     */
    public Ticket parkVehicle(Vehicle vehicle) {
        // Check if vehicle is already parked
        if (vehicle.isParked()) {
            System.out.println("Vehicle is already parked!");
            return null;
        }
        
        // Find available spot
        ParkingSpot spot = parkingLot.findAvailableSpot(vehicle);
        if (spot == null) {
            System.out.println("No available spots for " + vehicle.getType());
            return null;
        }
        
        // Park the vehicle
        if (vehicle.park(spot)) {
            Ticket ticket = ticketGenerator.generateTicket(vehicle, spot);
            activeTickets.put(ticket.getTicketNumber(), ticket);
            System.out.println("✓ Vehicle parked successfully!");
            System.out.println("  Spot: " + spot.getSpotId());
            System.out.println("  Ticket: " + ticket.getTicketNumber());
            return ticket;
        }
        
        return null;
    }
    
    /**
     * Processes vehicle exit and calculates fees.
     * 
     * @param ticketNumber The ticket number for the exiting vehicle
     * @return true if exit was successful, false otherwise
     */
    public boolean exitVehicle(String ticketNumber) {
        Ticket ticket = activeTickets.get(ticketNumber);
        
        if (ticket == null) {
            System.out.println("Invalid ticket number!");
            return false;
        }
        
        // Set exit time
        ticket.setExitTime();
        
        // Calculate fee
        double duration = ticket.calculateDuration();
        PaymentProcessor processor = new PaymentProcessor(ticket.getVehicle().getType());
        double fee = processor.calculateFee(duration);
        ticket.setFee(fee);
        
        // Display receipt
        System.out.println("\n" + processor.getReceipt(duration, fee));
        
        // Process payment
        if (processor.processPayment(fee)) {
            // Unpark vehicle
            ticket.getVehicle().unpark();
            activeTickets.remove(ticketNumber);
            System.out.println("\n✓ Vehicle exited successfully!");
            return true;
        }
        
        return false;
    }
    
    /**
     * Displays current parking lot status.
     */
    public void displayStatus() {
        System.out.println("\n========== PARKING LOT STATUS ==========");
        System.out.println(parkingLot);
        System.out.println("Small spots available: " + 
            parkingLot.countAvailableSpots(enums.SpotSize.SMALL));
        System.out.println("Medium spots available: " + 
            parkingLot.countAvailableSpots(enums.SpotSize.MEDIUM));
        System.out.println("Large spots available: " + 
            parkingLot.countAvailableSpots(enums.SpotSize.LARGE));
        System.out.println("Active tickets: " + activeTickets.size());
        System.out.println("========================================\n");
    }
    
    /**
     * Gets a ticket by its number.
     * 
     * @param ticketNumber The ticket number
     * @return The ticket, or null if not found
     */
    public Ticket getTicket(String ticketNumber) {
        return activeTickets.get(ticketNumber);
    }
    
    /**
     * Gets the parking lot.
     * 
     * @return The managed parking lot
     */
    public ParkingLot getParkingLot() {
        return parkingLot;
    }
}
