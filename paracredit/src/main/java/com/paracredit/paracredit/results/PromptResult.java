package com.paracredit.paracredit.results;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PromptResult {

    public List<Topic> topicsList;
    public List<Comparison> comparisonList;

    public List<Topic> getTopicsList() {
        return this.topicsList;
    }

    public void setTopicsList(List<Topic> topicsList) {
        this.topicsList =  topicsList;
    }

    public List<Comparison> getComparisonList() {
        return this.comparisonList;
    }

    public void setComparisonList(List<Comparison> comparisonList) {
        this.comparisonList =  comparisonList;
    }


}
