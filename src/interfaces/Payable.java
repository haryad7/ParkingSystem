package interfaces;

/**
 * Interface for entities that can process payments
 * Implemented by classes that handle payment operations
 * @author Paywast
 * @author Haryad
 */
public interface Payable {
    
    /**
     * Process a payment for a given amount
     * @param amount the amount to be paid
     * @return true if payment was successful, false otherwise
     */
    boolean processPayment(double amount);
    
    /**
     * Calculate the total amount due
     * @param hours number of hours parked
     * @param rate hourly parking rate
     * @return total amount to be paid
     */
    double calculateAmount(int hours, double rate);
    
    /**
     * Issue a refund for a given amount
     * @param amount the amount to be refunded
     * @return true if refund was successful, false otherwise
     */
    boolean issueRefund(double amount);
    
    /**
     * Get the payment status
     * @return string description of payment status
     */
    String getPaymentStatus();
    
    /**
     * Apply a discount to the payment amount
     * @param amount original amount
     * @param discountPercent discount percentage (0-100)
     * @return discounted amount
     */
    default double applyDiscount(double amount, double discountPercent) {
        if (discountPercent < 0 || discountPercent > 100) {
            return amount;
        }
        return amount * (1 - discountPercent / 100.0);
    }
    
    /**
     * Calculate tax on the payment amount
     * @param amount the base amount
     * @param taxRate tax rate as percentage (e.g., 8.5 for 8.5%)
     * @return total amount including tax
     */
    default double calculateWithTax(double amount, double taxRate) {
        if (taxRate < 0) {
            return amount;
        }
        return amount * (1 + taxRate / 100.0);
    }
    
    /**
     * Validate payment amount
     * @param amount the amount to validate
     * @return true if amount is valid (positive and reasonable)
     */
    default boolean validateAmount(double amount) {
        return amount > 0 && amount < 100; // Max $100 per transaction
    }
}