package com.paracredit.paracredit.domain;

public class BusinessDetails {
    public String businessName;
    public String businessConstitution;
    public String yearOfRegistration;
    public String annualTurnover;
    public String natureOfBusiness;
    public String revenue;
    public String cashFlow;
    public String profitMargin;
    public String existingDebt;
    public String collateral;

    public BusinessDetails(String businessName, String businessConstitution, String yearOfRegistration, String annualTurnover
    ,String natureOfBusiness, String revenue, String cashFlow, String profitMargin, String existingDebt, String collateral) {

        this.businessName = businessName;
        this.businessConstitution = businessConstitution;
        this.yearOfRegistration = yearOfRegistration;
        this.annualTurnover = annualTurnover;
        this.natureOfBusiness = natureOfBusiness;
        this.revenue = revenue;
        this.cashFlow = cashFlow;
        this.profitMargin = profitMargin;
        this.existingDebt = existingDebt;
        this.collateral = collateral;

    }



}
