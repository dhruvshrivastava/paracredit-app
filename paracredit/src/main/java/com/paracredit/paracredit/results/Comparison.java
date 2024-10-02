package com.paracredit.paracredit.results;

public class Comparison {
        public String creditOption;
        public String interestRate;
        public String maxLoanAmount;
        public String loanTenure;
        public String creditScore;
        public String suitabilityScore;
        public String eligibilityScore;
        public String reasonForSuitability;
        public String reasonForEligibility;



    public Comparison(String creditOption, String interestRate, String minimumToMaximumLoanAmount, String loanTenure, String creditScore, String suitabilityScore, String eligibilityScore, String reasonForSuitability, String reasonForEligibility) {
            this.creditOption = creditOption;
            this.interestRate = interestRate;
            this.maxLoanAmount = minimumToMaximumLoanAmount;
            this.loanTenure = loanTenure;
            this.creditScore= creditScore;
            this.suitabilityScore = suitabilityScore;
            this.eligibilityScore = eligibilityScore;
            this.reasonForEligibility = reasonForEligibility;
            this.reasonForSuitability = reasonForSuitability;

        }

        // Getters

        public String getCreditOption() {
            return this.creditOption;
        }

        public String getInterestRate() {
            return this.interestRate;
        }

        public String getMinimumToMaximumLoanAmount() {
            return this.maxLoanAmount;
        }

        public String getLoanTenure() {
            return this.loanTenure;
        }

        public String getCreditScore() {
            return this.creditScore;
        }

        public String getSuitabilityScore() {
            return this.suitabilityScore;
        }




}
