package com.paracredit.paracredit.prompts;

public class LearningPrompt {

    public String tenure;
    public String industry;
    public String previousHistory;
    public String interestLevel;
    public String prevKnowledge;

    public void setTenure(String tenure) {
        this.tenure = tenure;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public void setPreviousHistory(String previousHistory) {
        this.previousHistory = previousHistory;
    }

    public void setInterestLevel(String interestLevel) {
        this.interestLevel = interestLevel;
    }

    public void setPrevKnowledge(String prevKnowledge) {
        this.prevKnowledge = prevKnowledge;
    }

    @Override
    public String toString() {
        return String.format("A prospective MSME owner is {0} in learning about " +
                "government schemes and subsidies" +
                " for MSMEs, their current level of understanding is {1} and " +
                "they {2} applied for obtaining credit for their businesss, " +
                "the primary industry in which their business operates is {4} and " +
                "has been operatinal for {5}. List a personalised learning plan with topics and subtopics along with a link to a in depth resource for learning about the topic for the business owner using this JSON schema: \n Topic = {\\\"topic_name\\\": str, \\\"sub_topic_1_name\\\": str, \\\"sub_topic_2_name\\\": str, \\\"sub_topic_3_name\\\": str, \\\"resource_link\\\": str}\n" +
                "            Return: list[Topic]", interestLevel, prevKnowledge, previousHistory, industry, tenure);
    }



}
