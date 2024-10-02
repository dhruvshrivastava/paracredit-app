package com.paracredit.paracredit.controller;

import java.util.ArrayList;
import java.util.List;
import com.paracredit.paracredit.prompts.LearningPrompt;
import com.paracredit.paracredit.results.PromptResult;
import com.paracredit.paracredit.results.Topic;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class UniversityController {

    @Autowired
    PromptResult promptResult;

    String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=AIzaSyDkIgAqUeOER3CJD5muh1oGLXJgxiSoP0Q";

    @GetMapping("/university/index")
    public String home() {
        return "university-modules";
    }

    @GetMapping("/university/modules")
    public String modules() {
        return "university-modules";
    }

    @PostMapping("/university-quiz")
    public String universityQuiz() {
        return "university-quiz";
    }

    @PostMapping("/submitQuiz")
    public String submitQuiz(HttpServletRequest request, Model model) {
        // Retrieve the form data from the request parameters
        String tenure = request.getParameter("tenure");
        String industry = request.getParameter("industry");
        String previousHistory = request.getParameter("previousHistory");
        String previousKnowledge = request.getParameter("previousKnowledge");
        String interestLevel = request.getParameter("interestLevel");

        LearningPrompt prompt = new LearningPrompt();
        prompt.setIndustry(industry);
        prompt.setInterestLevel(interestLevel);
        prompt.setPreviousHistory(previousHistory);
        prompt.setPrevKnowledge(previousKnowledge);
        prompt.setTenure(tenure);

        String promptString = prompt.toString();
        RestTemplate restTemplate = new RestTemplate();

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject requestBody = new JSONObject();

        JSONObject generationConfig = new JSONObject();
        generationConfig.put("response_mime_type", "application/json");

        JSONObject textPart = new JSONObject();
        textPart.put("text", promptString);

        JSONObject parts = new JSONObject();
        parts.append("parts", textPart);

        requestBody.append("contents", parts);
        requestBody.put("generationConfig", generationConfig);
        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {

                JSONObject jsonObject = new JSONObject(response.getBody());
                JSONArray candidates = jsonObject.getJSONArray("candidates");

                List<Topic> topics = new ArrayList<>();

                for (int i = 0; i < candidates.length(); i++) {
                    JSONObject candidate = candidates.getJSONObject(i);
                    JSONObject content = candidate.getJSONObject("content");
                    JSONArray responseParts = content.getJSONArray("parts");

                    // Extract the 'text' from the first part
                    String text = responseParts.getJSONObject(0).getString("text");

                    // Parse the text as JSON to extract topics
                    JSONArray topicsArray = new JSONArray(text);
                    for (int j = 0; j < topicsArray.length(); j++) {
                        JSONObject topicJson = topicsArray.getJSONObject(j);

                        // Create a Topic object from the JSON
                        Topic topic = new Topic(
                                topicJson.getString("topic_name"),
                                topicJson.getString("sub_topic_1_name"),
                                topicJson.getString("sub_topic_2_name"),
                                topicJson.getString("sub_topic_3_name"),
                                topicJson.getString("resource_link")
                        );
                        topics.add(topic);
                    }
                    promptResult.setTopicsList(topics);
                }

            }

            else {
                System.out.println("Request failed with status code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("topicsList", promptResult.getTopicsList());
        return "learning-roadmap";
    }

    @GetMapping("/university/modules/types-of-credit")
    public String blog2() {
        return "blog2";
    }
    @GetMapping("/university/modules/negotiate-loan-terms")
    public String blog3() {
        return "blog3";
    }
    @GetMapping("/university/modules/credit-for-business-growth")
    public String blog4() {
        return "blog4";
    }
    @GetMapping("/university/modules/govt-schemes")
    public String blog5() {
        return "blog5";
    }
    @GetMapping("/university/modules/assess-creditworthiness")
    public String blog1() {
        return "blog1";
    }
}