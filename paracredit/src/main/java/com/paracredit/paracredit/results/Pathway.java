package com.paracredit.paracredit.results;

public class Pathway {
    private String loanToValueRatio;
    private String recommendedLoanAmount;
    private String recommendedLoanTenure;
    private String financialHealthPotentialImprovements;
    private String creditworthinessPotentialImprovements;
    private String feedback;
    private String expenseManagement;
    private String incomeStability;
    private String assetDiversification;
    private String creditHistoryLength;
    private String creditMix;
    private String deliquencyRisk;

    // Constructor
    public Pathway(String loanToValueRatio, String recommendedLoanAmount, String recommendedLoanTenure,
                   String financialHealthPotentialImprovements, String creditworthinessPotentialImprovements,
                   String feedback, String expenseManagement, String incomeStability, String assetDiversification,
                   String creditHistoryLength, String creditMix, String deliquencyRisk) {
        this.loanToValueRatio = loanToValueRatio;
        this.recommendedLoanAmount = recommendedLoanAmount;
        this.recommendedLoanTenure = recommendedLoanTenure;
        this.financialHealthPotentialImprovements = financialHealthPotentialImprovements;
        this.creditworthinessPotentialImprovements = creditworthinessPotentialImprovements;
        this.feedback = feedback;
        this.expenseManagement = expenseManagement;
        this.incomeStability = incomeStability;
        this.assetDiversification = assetDiversification;
        this.creditHistoryLength = creditHistoryLength;
        this.creditMix = creditMix;
        this.deliquencyRisk = deliquencyRisk;
    }

    // Getters and Setters
    public String getLoanToValueRatio() {
        return loanToValueRatio;
    }

    public void setLoanToValueRatio(String loanToValueRatio) {
        this.loanToValueRatio = loanToValueRatio;
    }

    public String getRecommendedLoanAmount() {
        return recommendedLoanAmount;
    }

    public void setRecommendedLoanAmount(String recommendedLoanAmount) {
        this.recommendedLoanAmount = recommendedLoanAmount;
    }

    public String getRecommendedLoanTenure() {
        return recommendedLoanTenure;
    }

    public void setRecommendedLoanTenure(String recommendedLoanTenure) {
        this.recommendedLoanTenure = recommendedLoanTenure;
    }

    public String getFinancialHealthPotentialImprovements() {
        return financialHealthPotentialImprovements;
    }

    public void setFinancialHealthPotentialImprovements(String financialHealthPotentialImprovements) {
        this.financialHealthPotentialImprovements = financialHealthPotentialImprovements;
    }

    public String getCreditworthinessPotentialImprovements() {
        return creditworthinessPotentialImprovements;
    }

    public void setCreditworthinessPotentialImprovements(String creditworthinessPotentialImprovements) {
        this.creditworthinessPotentialImprovements = creditworthinessPotentialImprovements;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getExpenseManagement() {
        return expenseManagement;
    }

    public void setExpenseManagement(String expenseManagement) {
        this.expenseManagement = expenseManagement;
    }

    public String getIncomeStability() {
        return incomeStability;
    }

    public void setIncomeStability(String incomeStability) {
        this.incomeStability = incomeStability;
    }

    public String getAssetDiversification() {
        return assetDiversification;
    }

    public void setAssetDiversification(String assetDiversification) {
        this.assetDiversification = assetDiversification;
    }

    public String getCreditHistoryLength() {
        return creditHistoryLength;
    }

    public void setCreditHistoryLength(String creditHistoryLength) {
        this.creditHistoryLength = creditHistoryLength;
    }

    public String getCreditMix() {
        return creditMix;
    }

    public void setCreditMix(String creditMix) {
        this.creditMix = creditMix;
    }

    public String getDeliquencyRisk() {
        return deliquencyRisk;
    }

    public void setDeliquencyRisk(String deliquencyRisk) {
        this.deliquencyRisk = deliquencyRisk;
    }
}
