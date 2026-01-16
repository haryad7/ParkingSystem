package services;

import models.Ticket;
import models.Vehicle;
import models.ParkingSpot;
import java.util.Random;

/**
 * Service class responsible for generating parking tickets.
 * Uses random number generation for unique ticket IDs.
 * 
 * @author Parking System Team
 * @version 1.0
 */
public class TicketGenerator {
    /** Random number generator for ticket numbers */
    private Random random;
    
    /**
     * Constructs a new TicketGenerator.
     */
    public TicketGenerator() {
        this.random = new Random();
    }
    
    /**
     * Generates a unique ticket number.
     * 
     * @return A unique ticket number string
     */
    private String generateTicketNumber() {
        return "TKT-" + System.currentTimeMillis() + "-" + random.nextInt(1000);
    }
    
    /**
     * Generates a new parking ticket for the specified vehicle and spot.
     * 
     * @param vehicle The vehicle being parked
     * @param spot The assigned parking spot
     * @return A new Ticket object
     */
    public Ticket generateTicket(Vehicle vehicle, ParkingSpot spot) {
        String ticketNumber = generateTicketNumber();
        return new Ticket(ticketNumber, vehicle, spot);
    }
}