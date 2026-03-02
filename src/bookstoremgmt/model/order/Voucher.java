package bookstoremgmt.model.order;

/**
 * Voucher class represents a discount voucher that can be applied to an order. It may contain properties such as voucher code, discount amount or percentage, and expiration date. This class can be expanded in the future to include methods for validating the voucher and calculating the discount on an order.
 * @author Nguyen Tran Duc Anh
 */
public class Voucher {
    private String code; // Unique code for the voucher
    private boolean isPercentage; // Indicates whether the discount is a percentage or a fixed amount
    private int discountValue; // The value of the discount (either percentage or fixed amount)
    private String minOrderValue; // Minimum order value required to use the voucher
    private String expirationDate; // Expiration date of the voucher

    /**
     * Default constructor initializes the voucher with default values
     */
    public Voucher() {
    }

    /**
     * Parameterized constructor to initialize all properties of the voucher
     */
    public Voucher(String code, boolean isPercentage, int discountValue, String minOrderValue, String expirationDate) {
        this.code = code;
        this.isPercentage = isPercentage;
        this.discountValue = discountValue;
        this.minOrderValue = minOrderValue;
        this.expirationDate = expirationDate;
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isPercentage() {
        return isPercentage;
    }

    public void setPercentage(boolean isPercentage) {
        this.isPercentage = isPercentage;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(int discountValue) {
        this.discountValue = discountValue;
    }

    public String getMinOrderValue() {
        return minOrderValue;
    }

    public void setMinOrderValue(String minOrderValue) {
        this.minOrderValue = minOrderValue;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
