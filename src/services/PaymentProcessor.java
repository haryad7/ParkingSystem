package services;

import interfaces.Payable;

/**
 * Service class for processing parking payments
 * Implements the Payable interface
 * @author Helen
 * @author Haryad
 */
public class PaymentProcessor implements Payable {
    
    private String paymentStatus;
    private double lastTransactionAmount;
    
    public PaymentProcessor() {
        this.paymentStatus = "No transactions yet";
        this.lastTransactionAmount = 0.0;
    }
    
    @Override
    public boolean processPayment(double amount) {
        if (amount <= 0) {
            System.out.println("ERROR: Invalid payment amount");
            paymentStatus = "FAILED - Invalid amount";
            return false;
        }
        
        // Simulate payment processing
        System.out.println("Processing payment of $" + String.format("%.2f", amount));
        lastTransactionAmount = amount;
        paymentStatus = "SUCCESS - Paid $" + String.format("%.2f", amount);
        
        return true;
    }
    
    @Override
    public double calculateAmount(int hours, double rate) {
        if (hours <= 0 || rate < 0) {
            return 0.0;
        }
        return hours * rate;
    }
    
    @Override
    public boolean issueRefund(double amount) {
        if (amount <= 0 || amount > lastTransactionAmount) {
            System.out.println("ERROR: Invalid refund amount");
            paymentStatus = "REFUND FAILED";
            return false;
        }
        
        System.out.println("Issuing refund of $" + String.format("%.2f", amount));
        paymentStatus = "REFUNDED - $" + String.format("%.2f", amount);
        
        return true;
    }
    
    @Override
    public String getPaymentStatus() {
        return paymentStatus;
    }
    
    /**
     * Generate a receipt for a payment
     * @param licensePlate vehicle license plate
     * @param hours hours parked
     * @param rate hourly rate
     * @return formatted receipt string
     */
    public String generateReceipt(String licensePlate, int hours, double rate) {
        double amount = calculateAmount(hours, rate);
        StringBuilder receipt = new StringBuilder();
        
        receipt.append("\n========== PAYMENT RECEIPT ==========\n");
        receipt.append("Vehicle: ").append(licensePlate).append("\n");
        receipt.append("Hours Parked: ").append(hours).append("\n");
        receipt.append("Hourly Rate: $").append(String.format("%.2f", rate)).append("\n");
        receipt.append("Total Amount: $").append(String.format("%.2f", amount)).append("\n");
        receipt.append("Status: ").append(paymentStatus).append("\n");
        receipt.append("=====================================\n");
        
        return receipt.toString();
    }
    
    /**
     * Process payment with discount
     * @param amount original amount
     * @param discountPercent discount percentage
     * @return true if payment successful
     */
    public boolean processPaymentWithDiscount(double amount, double discountPercent) {
        double discountedAmount = applyDiscount(amount, discountPercent);
        System.out.println("Original: $" + String.format("%.2f", amount));
        System.out.println("Discount: " + discountPercent + "%");
        System.out.println("Final: $" + String.format("%.2f", discountedAmount));
        return processPayment(discountedAmount);
    }
    
    /**
     * Process payment with tax
     * @param amount base amount
     * @param taxRate tax rate percentage
     * @return true if payment successful
     */
    public boolean processPaymentWithTax(double amount, double taxRate) {
        double totalWithTax = calculateWithTax(amount, taxRate);
        System.out.println("Subtotal: $" + String.format("%.2f", amount));
        System.out.println("Tax (" + taxRate + "%): $" + String.format("%.2f", totalWithTax - amount));
        System.out.println("Total: $" + String.format("%.2f", totalWithTax));
        return processPayment(totalWithTax);
    }
}