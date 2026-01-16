package services;

import interfaces.Payable;
import enums.VehicleType;

/**
 * Service class for processing payments and calculating parking fees.
 * Implements different pricing strategies based on vehicle type.
 * 
 * @author Parking System Team
 * @version 1.0
 */
public class PaymentProcessor implements Payable {
    /** Hourly rate for motorcycles */
    private static final double MOTORCYCLE_RATE = 2.0;
    
    /** Hourly rate for cars */
    private static final double CAR_RATE = 5.0;
    
    /** Hourly rate for trucks */
    private static final double TRUCK_RATE = 8.0;
    
    /** Vehicle type for this payment */
    private VehicleType vehicleType;
    
    /**
     * Constructs a new PaymentProcessor for the specified vehicle type.
     * 
     * @param vehicleType The type of vehicle being charged
     */
    public PaymentProcessor(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }
    
    @Override
    public double calculateFee(double hours) {
        double rate = getHourlyRate();
        return hours * rate;
    }
    
    /**
     * Gets the hourly rate based on vehicle type.
     * 
     * @return The hourly parking rate
     */
    private double getHourlyRate() {
        switch (vehicleType) {
            case MOTORCYCLE:
                return MOTORCYCLE_RATE;
            case CAR:
                return CAR_RATE;
            case TRUCK:
                return TRUCK_RATE;
            default:
                return CAR_RATE;
        }
    }
    
    @Override
    public boolean processPayment(double amount) {
        // In a real system, this would integrate with a payment gateway
        System.out.println("Processing payment of $" + String.format("%.2f", amount));
        System.out.println("Payment successful!");
        return true;
    }
    
    /**
     * Gets a receipt string for the payment.
     * 
     * @param hours Number of hours parked
     * @param amount Total amount paid
     * @return Formatted receipt string
     */
    public String getReceipt(double hours, double amount) {
        return String.format(
            "========== RECEIPT ==========\n" +
            "Vehicle Type: %s\n" +
            "Hourly Rate: $%.2f\n" +
            "Duration: %.1f hours\n" +
            "Total Fee: $%.2f\n" +
            "=============================",
            vehicleType, getHourlyRate(), hours, amount
        );
    }
}
