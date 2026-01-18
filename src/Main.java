import models.*;
import services.*;

/**
 * Main class to demonstrate the Parking System
 * Tests all features: parking, ticketing, payment processing
 * Uses only OOP concepts: polymorphism, interfaces, abstract classes, composition
 * @author Haryad
 */
public class Main {
    
    public static void main(String[] args) {
        
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   PARKING MANAGEMENT SYSTEM - DEMO    ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        
        // COMPOSITION: ParkingLot contains ParkingSpots
        ParkingLot lot = new ParkingLot("Downtown Parking", "123 Main Street", 20);
        
        // Services use composition to work with the parking lot
        ParkingManager manager = new ParkingManager(lot);
        PaymentProcessor paymentProcessor = new PaymentProcessor();
        TicketGenerator ticketGenerator = new TicketGenerator();
        
        // Display initial status
        System.out.println("===== INITIAL PARKING LOT STATUS =====");
        manager.displayStatus();
        
        // SPECIALIZATION: Car, Motorcycle, Truck extend Vehicle
        System.out.println("\n===== CREATING VEHICLES =====");
        Car car1 = new Car("ABC123", "Red", "Toyota", "Camry", 2022);
        Car car2 = new Car("XYZ789", "Blue", "Honda", "Accord", 2021);
        Motorcycle moto1 = new Motorcycle("MOTO01", "Black", "Harley", "Sportster", 2023, false);
        Motorcycle moto2 = new Motorcycle("MOTO02", "Silver", "Yamaha", "YZF", 2022, true);
        Truck truck1 = new Truck("TRK001", "White", "Ford", "F-150", 2023, 2.5);
        Truck truck2 = new Truck("TRK002", "Gray", "Chevrolet", "Silverado", 2022, 3.5);
        
        System.out.println("Created 6 vehicles:");
        System.out.println("  - " + car1);
        System.out.println("  - " + car2);
        System.out.println("  - " + moto1);
        System.out.println("  - " + moto2);
        System.out.println("  - " + truck1);
        System.out.println("  - " + truck2);
        
        // POLYMORPHISM: All vehicles are treated as Vehicle type
        System.out.println("\n\n===== SCENARIO 1: POLYMORPHISM DEMO =====");
        System.out.println("Different vehicle types, same interface:");
        demonstratePolymorphism(car1, moto1, truck1);
        
        // Test Scenario 2: Park vehicles and generate tickets
        System.out.println("\n\n===== SCENARIO 2: PARKING VEHICLES =====");
        parkAndTicket(manager, ticketGenerator, car1);
        parkAndTicket(manager, ticketGenerator, car2);
        parkAndTicket(manager, ticketGenerator, moto1);
        parkAndTicket(manager, ticketGenerator, moto2);
        parkAndTicket(manager, ticketGenerator, truck1);
        parkAndTicket(manager, ticketGenerator, truck2);
        
        // Display current status
        System.out.println("\n===== CURRENT STATUS AFTER PARKING =====");
        manager.displayStatus();
        ticketGenerator.displayActiveTickets();
        
        // Test Scenario 3: Try to park duplicate vehicle
        System.out.println("\n===== SCENARIO 3: DUPLICATE PARKING ATTEMPT =====");
        parkAndTicket(manager, ticketGenerator, car1);
        
        // Test Scenario 4: Check ticket details
        System.out.println("\n===== SCENARIO 4: TICKET DETAILS =====");
        Ticket ticket1 = ticketGenerator.findActiveTicket("ABC123");
        if (ticket1 != null) {
            ticket1.displayTicket();
        }
        
        // Test Scenario 5: Vehicle exit and payment
        System.out.println("\n===== SCENARIO 5: VEHICLE EXIT & PAYMENT =====");
        exitAndPay(manager, ticketGenerator, paymentProcessor, "ABC123");
        
        // Test Scenario 6: Another vehicle exit
        System.out.println("\n===== SCENARIO 6: ANOTHER EXIT & PAYMENT =====");
        exitAndPay(manager, ticketGenerator, paymentProcessor, "MOTO01");
        
        // Test Scenario 7: Display statistics
        System.out.println("\n===== SCENARIO 7: SYSTEM STATISTICS =====");
        manager.displayStatistics();
        ticketGenerator.displayStatistics();
        
        // Test Scenario 8: Park new vehicles in freed spots
        System.out.println("\n===== SCENARIO 8: PARK NEW VEHICLES =====");
        Car car3 = new Car("NEW001", "Green", "Tesla", "Model 3", 2024);
        Motorcycle moto3 = new Motorcycle("NEW002", "Orange", "Ducati", "Monster", 2024, false);
        
        parkAndTicket(manager, ticketGenerator, car3);
        parkAndTicket(manager, ticketGenerator, moto3);
        
        // Test Scenario 9: Find vehicle location
        System.out.println("\n===== SCENARIO 9: FIND VEHICLE LOCATION =====");
        findVehicleLocation(manager, "XYZ789");
        findVehicleLocation(manager, "TRK002");
        findVehicleLocation(manager, "NOTFOUND");
        
        // Test Scenario 10: Mass exit simulation
        System.out.println("\n===== SCENARIO 10: MASS EXIT =====");
        exitAndPay(manager, ticketGenerator, paymentProcessor, "XYZ789");
        exitAndPay(manager, ticketGenerator, paymentProcessor, "MOTO02");
        exitAndPay(manager, ticketGenerator, paymentProcessor, "TRK001");
        
        // Test Scenario 11: Interface demonstration
        System.out.println("\n===== SCENARIO 11: INTERFACE POLYMORPHISM =====");
        demonstrateInterfaces(manager, paymentProcessor);
        
        // Test Scenario 12: Final statistics
        System.out.println("\n===== SCENARIO 12: FINAL STATISTICS =====");
        System.out.println("\n--- Parking Lot Statistics ---");
        manager.displayStatistics();
        
        System.out.println("\n--- Ticket Statistics ---");
        ticketGenerator.displayStatistics();
        
        System.out.println("\n--- Payment Summary ---");
        System.out.println("Total Revenue Collected: $" + 
                         String.format("%.2f", ticketGenerator.getTotalRevenue()));
        System.out.println("Total Unpaid Fees: $" + 
                         String.format("%.2f", ticketGenerator.getTotalUnpaid()));
        
        // Summary
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║          DEMO COMPLETED!              ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("\nSummary:");
        System.out.println("  Total Vehicles Created: " + Vehicle.getVehicleCount());
        System.out.println("  Total Parking Lots: " + ParkingLot.getParkingLotCount());
        System.out.println("  Total Spots Created: " + ParkingSpot.getTotalSpotsCreated());
        System.out.println("  Total Tickets Issued: " + Ticket.getTicketCounter());
        System.out.println("  Current Occupancy: " + manager.getCurrentOccupancy() + "/" + lot.getTotalCapacity());
        
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       OOP CONCEPTS DEMONSTRATED       ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("✓ Polymorphism: Vehicle hierarchy");
        System.out.println("✓ Abstract Class: Vehicle base class");
        System.out.println("✓ Interfaces: Parkable, Payable");
        System.out.println("✓ Composition: ParkingLot has ParkingSpots");
        System.out.println("✓ Specialization: Car, Motorcycle, Truck extend Vehicle");
        System.out.println("✓ Encapsulation: Private fields with getters/setters");
    }
    
    /**
     * POLYMORPHISM DEMO
     * Same method works with different vehicle types
     */
    private static void demonstratePolymorphism(Vehicle v1, Vehicle v2, Vehicle v3) {
        System.out.println("\nPolymorphism Example:");
        System.out.println("All vehicles use the same method calls:");
        
        // Array of different vehicle types
        Vehicle[] vehicles = {v1, v2, v3};
        
        for (Vehicle v : vehicles) {
            // Same method call works for all vehicle types
            System.out.println("  " + v.getType() + " rate: $" + v.getParkingRate() + "/hr");
            System.out.println("    3 hours fee: $" + v.calculateParkingFee(3));
        }
    }
    
    /**
     * Helper method: Park a vehicle and generate a ticket
     */
    private static void parkAndTicket(ParkingManager manager, TicketGenerator ticketGen, Vehicle vehicle) {
        System.out.println("\n--- Parking: " + vehicle.getLicensePlate() + " ---");
        ParkingSpot spot = manager.park(vehicle);
        
        if (spot != null) {
            Ticket ticket = ticketGen.generateTicket(vehicle, spot);
            if (ticket != null) {
                System.out.println("✓ Ticket " + ticket.getTicketId() + " issued");
            }
        }
    }
    
    /**
     * Helper method: Exit vehicle and process payment
     */
    private static void exitAndPay(ParkingManager manager, TicketGenerator ticketGen, 
                                   PaymentProcessor payment, String licensePlate) {
        System.out.println("\n--- Exiting: " + licensePlate + " ---");
        
        // Find and complete the ticket
        Ticket ticket = ticketGen.completeTicket(licensePlate);
        
        if (ticket != null) {
            System.out.println("Hours parked: " + ticket.calculateHoursParked());
            System.out.println("Parking fee: $" + String.format("%.2f", ticket.getParkingFee()));
            
            // Process payment
            boolean paymentSuccess = payment.processPayment(ticket.getParkingFee());
            
            if (paymentSuccess) {
                ticket.markAsPaid();
                System.out.println("✓ Payment successful");
                
                // Remove vehicle from lot
                Vehicle removed = manager.unpark(licensePlate);
                if (removed != null) {
                    System.out.println("✓ Vehicle removed from parking lot");
                }
                
                // Print receipt
                String receipt = payment.generateReceipt(
                    licensePlate, 
                    ticket.calculateHoursParked(), 
                    ticket.getVehicle().getParkingRate()
                );
                System.out.println(receipt);
            }
        }
    }
    
    /**
     * Helper method: Find vehicle location
     */
    private static void findVehicleLocation(ParkingManager manager, String licensePlate) {
        System.out.println("\n--- Finding: " + licensePlate + " ---");
        ParkingSpot spot = manager.findVehicleLocation(licensePlate);
        
        if (spot != null) {
            System.out.println("✓ Found in spot #" + spot.getSpotNumber() + 
                             " (" + spot.getSize() + ")");
            System.out.println("  Vehicle: " + spot.getCurrentVehicle());
        } else {
            System.out.println("✗ Vehicle not found in parking lot");
        }
    }
    
    /**
     * INTERFACE DEMO
     * Demonstrate how interfaces allow different implementations
     */
    private static void demonstrateInterfaces(ParkingManager manager, PaymentProcessor payment) {
        System.out.println("Interface Polymorphism Demo:");
        
        // Both implement different interfaces
        System.out.println("\n1. Parkable Interface (ParkingManager implements it):");
        System.out.println("   - Has available space? " + manager.hasAvailableSpace());
        System.out.println("   - Current occupancy: " + manager.getCurrentOccupancy());
        System.out.println("   - Is full? " + manager.isFull());
        System.out.println("   - Is empty? " + manager.isEmpty());
        
        System.out.println("\n2. Payable Interface (PaymentProcessor implements it):");
        double testAmount = payment.calculateAmount(5, 2.5);
        System.out.println("   - Calculate 5 hours at $2.50/hr: $" + 
                         String.format("%.2f", testAmount));
        System.out.println("   - Payment status: " + payment.getPaymentStatus());
        System.out.println("   - Validate $100: " + payment.validateAmount(100));
        System.out.println("   - Validate -$50: " + payment.validateAmount(-50));
        
        System.out.println("\n→ Interfaces define contracts that classes must fulfill!");
    }
}
