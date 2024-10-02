package com.paracredit.paracredit.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "loan_products")
public class LoanProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "eligibility_criteria")
    private String eligibilityCriteria;

    @Column(name = "max_loan_amount")
    private String maxLoanAmount;

    @Column(name = "interest_rate")
    private String interestRate;

    @Column(name = "processing_fee")
    private String processingFee;

    @Column(name = "max_tenure")
    private String maxTenure;

    @Column(name = "collateral")
    private String collateral;

    private String tooltip;

    // Getters and Setters
    public LoanProduct(Long id, String productType, String eligibilityCriteria, String maxLoanAmount, String interestRate, String processingFee,
                       String maxTenure, String collateral) {
        this.id = id;
        this.productType = productType;
        this.eligibilityCriteria = eligibilityCriteria;
        this.maxLoanAmount = maxLoanAmount;
        this.interestRate = interestRate;
        this.processingFee = processingFee;
        this.maxTenure = maxTenure;
        this.collateral = collateral;
        this.tooltip = productType;
    }

    // Default constructor
    public LoanProduct() {
    }


    public String getTooltip() {
        return this.tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getEligibilityCriteria() {
        return eligibilityCriteria;
    }

    public void setEligibilityCriteria(String eligibilityCriteria) {
        this.eligibilityCriteria = eligibilityCriteria;
    }

    public String getMaxLoanAmount() {
        return maxLoanAmount;
    }

    public void setMaxLoanAmount(String maxLoanAmount) {
        this.maxLoanAmount = maxLoanAmount;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(String processingFee) {
        this.processingFee = processingFee;
    }

    public String getMaxTenure() {
        return maxTenure;
    }

    public void setMaxTenure(String maxTenure) {
        this.maxTenure = maxTenure;
    }

    public String getCollateral() {
        return collateral;
    }

    public void setCollateral(String collateral) {
        this.collateral = collateral;
    }
}

