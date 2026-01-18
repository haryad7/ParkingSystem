package services;

import models.Ticket;
import models.Vehicle;
import models.ParkingSpot;
import java.util.ArrayList;

/**
 * Service class for generating and managing parking tickets
 * Maintains a registry of all issued tickets using ArrayList only
 * @author Helen
 * @author Haryad
 */
public class TicketGenerator {
    
    private ArrayList<Ticket> activeTickets;
    private ArrayList<Ticket> completedTickets;
    
    public TicketGenerator() {
        this.activeTickets = new ArrayList<>();
        this.completedTickets = new ArrayList<>();
    }
    
    /**
     * Generate a new parking ticket
     * @param vehicle the vehicle being parked
     * @param spot the parking spot assigned
     * @return the generated Ticket
     */
    public Ticket generateTicket(Vehicle vehicle, ParkingSpot spot) {
        if (vehicle == null || spot == null) {
            System.out.println("ERROR: Cannot generate ticket - invalid vehicle or spot");
            return null;
        }
        
        // Check if vehicle already has an active ticket
        if (findActiveTicketByPlate(vehicle.getLicensePlate()) != null) {
            System.out.println("ERROR: Vehicle " + vehicle.getLicensePlate() + 
                             " already has an active ticket");
            return null;
        }
        
        Ticket ticket = new Ticket(vehicle, spot);
        activeTickets.add(ticket);
        
        System.out.println("Ticket generated: " + ticket.getTicketId() + 
                         " for vehicle " + vehicle.getLicensePlate());
        
        return ticket;
    }
    
    /**
     * Complete a ticket when vehicle exits
     * @param licensePlate the vehicle's license plate
     * @return the completed Ticket, or null if not found
     */
    public Ticket completeTicket(String licensePlate) {
        Ticket ticket = findActiveTicketByPlate(licensePlate);
        
        if (ticket == null) {
            System.out.println("ERROR: No active ticket found for " + licensePlate);
            return null;
        }
        
        ticket.completeTicket();
        activeTickets.remove(ticket);
        completedTickets.add(ticket);
        
        System.out.println("Ticket completed: " + ticket.getTicketId());
        
        return ticket;
    }
    
    /**
     * Find an active ticket by license plate
     * Uses simple loop through ArrayList
     * @param licensePlate the vehicle's license plate
     * @return the active Ticket, or null if not found
     */
    private Ticket findActiveTicketByPlate(String licensePlate) {
        for (Ticket ticket : activeTickets) {
            if (ticket.getVehicle().getLicensePlate().equals(licensePlate)) {
                return ticket;
            }
        }
        return null;
    }
    
    /**
     * Find an active ticket by license plate (public version)
     * @param licensePlate the vehicle's license plate
     * @return the active Ticket, or null if not found
     */
    public Ticket findActiveTicket(String licensePlate) {
        return findActiveTicketByPlate(licensePlate);
    }
    
    /**
     * Find a ticket by ticket ID (searches both active and completed)
     * @param ticketId the ticket ID to search for
     * @return the Ticket, or null if not found
     */
    public Ticket findTicketById(String ticketId) {
        // Search active tickets
        for (Ticket ticket : activeTickets) {
            if (ticket.getTicketId().equals(ticketId)) {
                return ticket;
            }
        }
        
        // Search completed tickets
        for (Ticket ticket : completedTickets) {
            if (ticket.getTicketId().equals(ticketId)) {
                return ticket;
            }
        }
        
        return null;
    }
    
    /**
     * Get all active tickets
     * @return ArrayList of active tickets
     */
    public ArrayList<Ticket> getActiveTickets() {
        return new ArrayList<>(activeTickets);
    }
    
    /**
     * Get all completed tickets
     * @return ArrayList of completed tickets
     */
    public ArrayList<Ticket> getCompletedTickets() {
        return completedTickets;
    }
    
    /**
     * Get count of active tickets
     * @return number of active tickets
     */
    public int getActiveTicketCount() {
        return activeTickets.size();
    }
    
    /**
     * Get count of completed tickets
     * @return number of completed tickets
     */
    public int getCompletedTicketCount() {
        return completedTickets.size();
    }
    
    /**
     * Calculate total revenue from all completed tickets
     * @return total revenue
     */
    public double getTotalRevenue() {
        double total = 0.0;
        for (Ticket ticket : completedTickets) {
            if (ticket.isPaid()) {
                total += ticket.getParkingFee();
            }
        }
        return total;
    }
    
    /**
     * Calculate total unpaid fees from completed tickets
     * @return total unpaid amount
     */
    public double getTotalUnpaid() {
        double total = 0.0;
        for (Ticket ticket : completedTickets) {
            if (!ticket.isPaid()) {
                total += ticket.getParkingFee();
            }
        }
        return total;
    }
    
    /**
     * Display all active tickets
     */
    public void displayActiveTickets() {
        System.out.println("\n========== ACTIVE TICKETS ==========");
        if (activeTickets.isEmpty()) {
            System.out.println("No active tickets");
        } else {
            for (Ticket ticket : activeTickets) {
                System.out.println(ticket);
            }
        }
        System.out.println("Total Active: " + activeTickets.size());
        System.out.println("====================================\n");
    }
    
    /**
     * Display ticket statistics
     */
    public void displayStatistics() {
        System.out.println("\n========== TICKET STATISTICS ==========");
        System.out.println("Active Tickets: " + getActiveTicketCount());
        System.out.println("Completed Tickets: " + getCompletedTicketCount());
        System.out.println("Total Revenue (Paid): $" + String.format("%.2f", getTotalRevenue()));
        System.out.println("Total Unpaid: $" + String.format("%.2f", getTotalUnpaid()));
        System.out.println("=======================================\n");
    }
}