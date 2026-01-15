package interfaces;

/**
 * Interface defining the contract for payment processing.
 * Implements strategy pattern for different payment methods.
 * 
 * @author Parking System Team
 * @version 1.0
 */
public interface Payable {
    /**
     * Processes a payment for the specified amount.
     * 
     * @param amount The amount to be paid
     * @return true if payment was successful, false otherwise
     */
    boolean processPayment(double amount);
    
    /**
     * Calculates the parking fee based on duration.
     * 
     * @param hours The number of hours parked
     * @return The total fee to be paid
     */
    double calculateFee(double hours);
}