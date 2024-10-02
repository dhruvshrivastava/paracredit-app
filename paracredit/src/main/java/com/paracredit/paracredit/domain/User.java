package com.paracredit.paracredit.domain;

public class User {
    public String name;
    public String mobile;
    public String email;
    public String dob;
    public String pan;
    public String city;
    public String typeOfLoan;
    public BusinessDetails businessDetails;
    public CreditRequirements creditRequirements;

    public User(String name, String mobile, String email, String dob, String pan, String city, String typeOfLoan, BusinessDetails businessDetails, CreditRequirements creditRequirements) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.dob = dob;
        this.pan = pan;
        this.city = city;
        this.typeOfLoan = typeOfLoan;
        this.businessDetails = businessDetails;
        this.creditRequirements = creditRequirements;
    }

    public String toString() {
        return "Details: \n" + " Type of Loan: " + typeOfLoan
                + " Business Constitution: " + businessDetails.businessConstitution
                + " Annual Turnover: " + businessDetails.annualTurnover
                + " Nature of Business: " + businessDetails.natureOfBusiness
                + " Revenue: " + businessDetails.revenue +
                " Cash Flow: " + businessDetails.cashFlow +
                " Profit Margin: " + businessDetails.profitMargin +
                " Existing Debt: " + businessDetails.existingDebt +
                " Collateral: " + businessDetails.collateral +
                " Purpose of opting for Credit: " + creditRequirements.purpose +
                " Credit Amount Required: " + creditRequirements.amountRequired +
                " Repayment Cycle: " + creditRequirements.repaymentCycle +
                " Risk Appetite: " + creditRequirements.riskAppetite +
                " Flexibility: " + creditRequirements.flexibility;
    }

}
