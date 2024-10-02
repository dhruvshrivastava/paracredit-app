package com.paracredit.paracredit.domain;

public class CreditRequirements {

    public String purpose;
    public String amountRequired;
    public String repaymentCycle;
    public String riskAppetite;
    public String flexibility;

    public CreditRequirements(String purpose, String amountRequired, String repaymentCycle, String riskAppetite, String flexibility) {
        this.purpose = purpose;
        this.amountRequired = amountRequired;
        this.repaymentCycle = repaymentCycle;
        this.riskAppetite = riskAppetite;
        this.flexibility = flexibility;
    }


}
