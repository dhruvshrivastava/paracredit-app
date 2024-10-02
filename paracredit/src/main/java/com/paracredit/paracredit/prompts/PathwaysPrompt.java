package com.paracredit.paracredit.prompts;

public class PathwaysPrompt {
    public String toPrompt(String requirementJson, String goalsJson, String businessDetailsJson) {
        return "You are a financial advisor to a MSME. You have to perform financial health assessment, creditworthiness assessment and credit needs assessment of your client. For financial health assessment, compute the income stability (options are Stable, Moderate and Unstable), expense management (options are Efficient, Moderate, Inefficient), asset diversification (options are Highly Diversidied, Moderately Diverisified, Not Diversified). Also return the detailed potential improvements the client can make in this area. Answer in detail.\n"
                + "For creditworthiness assessment, compute credit history length (options are Established, Moderate, New), credit mix (options are Diverse, Limited, Singl-Type), deliquency risk (options are low risk, moderate risk, high risk). Aslo return the detailed potential improvements the client can make in this area. Give detailed response."
                + " For credit need assessment, you have to perform numerical metric such as loan to value ratio, recommended loan amount (answer with unit lakhs), recommended loan tenure (answer with unit months). Also return some detailed feedback for the client in this area. give detailed feedback.\n" +
                "Following are the JSON input data you should utilize to perform your assessment: \n" +
                 "<requirementJson>" + requirementJson +"</requirementJson>\n"
                + "<goalsJson>" + goalsJson + "</goalsJson>\n" +
                "<businessDetailsJson>" + businessDetailsJson + "</businessDetailsJson>\n" +
                "Make sure to return your response in the following schema. It is mandatory to answer for every metric. Do not return NA or empty response. Make sure that the JSON syntax is correct and the schema is followed correctly. Double check the JSON reponse to ensure it is correct and there are no issues.\n" +
                "\\n Pathway = {\\\\\\\"incomeStability\\\\\\\": str, \\\\\\\"expenseManagement\\\\\\\": str, \\\\\\\"assetDiversification\\\\\\\": str, \\\\\\\"financialHealthPotentialImprovements\\\\\\\": str, \\\\\\\"creditHistoryLength\\\\\\\": str, \\\\\\\"creditMix\\\\\\\": str, \\\\\\\"deliquencyRisk\\\\\\\": str, \\\\\\\"creditworthinessPotentialImprovements\\\\\\\": str, \\\\\\\"loanToValueRatio\\\\\\\": str, \\\\\\\"recommendedLoanAmount\\\\\\\": str, \\\\\\\"recommendedLoanTenure\\\\\\\": str, \\\\\\\"feedback\\\\\\\": str,}\\n\" +\n" +
                "                \"            Return: list[Pathway]\"";
    }
}
