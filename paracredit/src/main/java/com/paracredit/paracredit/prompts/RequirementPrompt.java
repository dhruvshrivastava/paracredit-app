package com.paracredit.paracredit.prompts;

public class RequirementPrompt {
    public String toPrompt(String userDetails) {
        return "You are a credit discovery platform for MSMEs. I'm going to give you the user details, business details and the credit requirements of your client's business. Extract the top 5 requirements that the user's business would need in a suitable credit option. The requirements should accurately describe what the user is looking for in a suitable credit option based on the user details provided" + "The details are as follows: " + userDetails + "\n" + "Return your response strictly in the following JSON Schema. Stricly follow the JSON schema, make sure to put the values in the correct variables provided. Make sure the syntax is correct and you are strictly following the schema provided" +
                "\\n Requirement = {\\\\\\\"requirement1\\\\\\\": str, \\\\\\\"requirement2\\\\\\\": str, \\\\\\\"requirement3\\\\\\\": str, \\\\\\\"requirement4\\\\\\\": str, \\\\\\\"requirement5\\\\\\\": str,}\\n\" +\n" +
                "                \"            Return: list[Requirement]\"";
    }
}
