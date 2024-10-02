package com.paracredit.paracredit.prompts;

public class GoalsPrompt {
    public String toPrompt(String userDetails) {
        return "You are a credit discovery platform for MSMEs. I'm going to give you the user details, business details and the credit requirements of your client's business. Extract the top 5 goals of the business. The goals should accurately describe what are the top 5 objectives that the user is targeting with the help of the suitable credit option" + "The details are as follows: " + userDetails + "\n" + "Return your response strictly in the following JSON Schema. Stricly follow the JSON schema, make sure to put the values in the correct variables provided. The JSON result should be incorrect. Make sure the syntax is correct and you are strictly following the schema provided" +
                "\\n Goal = {\\\\\\\"goal1\\\\\\\": str, \\\\\\\"goal2\\\\\\\": str, \\\\\\\"goal3\\\\\\\": str, \\\\\\\"goal4\\\\\\\": str, \\\\\\\"goal5\\\\\\\": str,}\\n\" +\n" +
                "                \"            Return: list[Goal]\"";
    }
}
