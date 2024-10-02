package com.paracredit.paracredit.results;

// Topic class to store the extracted data
public class Topic {
    String topicName;
    String subTopic1Name;
    String subTopic2Name;
    String subTopic3Name;
    String resourceLink;


    // Constructor
    public Topic(String topicName, String subTopic1Name, String subTopic2Name, String subTopic3Name, String resourceLink) {
        this.topicName = topicName;
        this.subTopic1Name = subTopic1Name;
        this.subTopic2Name = subTopic2Name;
        this.subTopic3Name = subTopic3Name;
        this.resourceLink = resourceLink;
    }

    // Getters
    public String getTopicName() { return topicName; }
    public String getSubTopic1Name() { return subTopic1Name; }
    public String getSubTopic2Name() { return subTopic2Name; }
    public String getSubTopic3Name() { return subTopic3Name; }
    public String getResourceLink() { return resourceLink;}


    @Override
    public String toString() {
        return "Topic: " + topicName + "\n" +
                "  Sub Topic 1: " + subTopic1Name + "\n" +
                "  Sub Topic 2: " + subTopic2Name + "\n" +
                "  Sub Topic 3: " + subTopic3Name + "\n" +
                "  Resource Link: " + resourceLink + "\n";
    }


}