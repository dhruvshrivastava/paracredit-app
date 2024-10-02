package com.paracredit.paracredit.prompts;

public class ComparisonPrompt {

    public String user_business;
    public String user_industry;
    public String user_revenue;
    public String user_cash_flow;
    public String creditOption1;
    public String creditOption2;

    public void setUser_business(String user_business) {
        this.user_business = user_business;
    }

    public void setUser_industry(String user_industry) {
        this.user_industry = user_industry;
    }

    public void setUser_revenue(String user_revenue) {
        this.user_revenue = user_revenue;
    }

    public void setUser_cash_flow(String user_cash_flow) {
        this.user_cash_flow = user_cash_flow;
    }

    public void setCreditOption1(String creditOption1) {
        this.creditOption1 = creditOption1;
    }

    public void setCreditOption2(String creditOption2) {
        this.creditOption2 = creditOption2;
    }
    public String getCashFlow() {
        return this.user_cash_flow;
    }

    public String toPrompt(String requirementJson, String businessJson) {
        return "You are a credit option comparison tool for MSMEs'. I am giving you the client's requirements and business details. I am also giving you two credit options. You have to compare these credit options and calculate a suitability score based on how suitable is the credit option for the client. You also have to give me an estimated eligible credit score, the interest rate, loan tenure, max loan amount for the credit option. Also based on the details of the client provided, calculate a eligibility score out of 10 that will depict how closely is the client meeting the lender's eligibility criteria" + "\n"
                + "Credit Option 1: " + creditOption1 + " Credit Option 2: " + creditOption2 +
                "The user's and requirements details are givin in JSON format below: " + "\n" +
                "<requirementJson> " + requirementJson + "</requirementJson>" + "\n"
                + "<businessJson>" + businessJson + "</businessJson>" + "\n"
                + "Also give the reason for both the suitability and eligibility scores. Make to return a value for every metric, do not return empty response or NA response. Make sure to answer with correct units as well. Add percentage sign, months and lakhs as applicable. Give the suitability score out of 10. Return your response strictly in the JSON Schema given below. Make sure that the JSON syntax is correct and you are using the appropriate variables:" + "\n"
                +  "\\n Comparison = {\\\\\\\"creditOption\\\\\\\": str, \\\\\\\"creditScore\\\\\\\": str, \\\\\\\"loanTenure\\\\\\\": str, \\\\\\\"interestRate\\\\\\\": str, \\\\\\\"maxLoanAmount\\\\\\\": str, \\\\\\\"suitabilityScore\\\\\\\": str, \\\\\\\"reasonForSuitability\\\\\\\": str, \\\\\\\"eligibilityScore\\\\\\\": str ,\\\\\\\"reasonForEligibility\\\\\\\": str}\\n\" +\n" +
                "                \"            Return: list[Comparison]\"";    }


}
