package storemanagement.model.order;

/**
 * CustomerVoucher class represents the association between a customer and a
 * voucher. It can be used to track which vouchers have been assigned to which
 * customers, and whether they have been used or not. This class can be expanded
 * in the future to include properties such as usage status, date of assignment,
 * and methods for validating the voucher for a specific customer.
 * 
 * @author Nguyen Tran Duc Anh
 */
public class CustomerVoucher {
    private String customerId; // Identifier for the customer
    private String voucherCode; // Code of the voucher assigned to the customer
    private boolean isUsed; // Indicates whether the voucher has been used by the customer

    /**
     * Default constructor initializes the customer voucher with default values
     */
    public CustomerVoucher() {
    }

    /**
     * Parameterized constructor to initialize all properties of the customer
     * voucher
     */
    public CustomerVoucher(String customerId, String voucherCode, boolean isUsed) {
        this.customerId = customerId;
        this.voucherCode = voucherCode;
        this.isUsed = isUsed;
    }

    // Getters and Setters
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
}
