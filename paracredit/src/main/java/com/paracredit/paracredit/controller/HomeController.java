package com.paracredit.paracredit.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.json.Json;
import com.paracredit.paracredit.domain.BusinessDetails;
import com.paracredit.paracredit.domain.CreditRequirements;
import com.paracredit.paracredit.domain.User;
import com.paracredit.paracredit.entity.LoanProduct;
import com.paracredit.paracredit.entity.LoanScheme;
import com.paracredit.paracredit.prompts.*;
import com.paracredit.paracredit.repository.LoanProductRepository;
import com.paracredit.paracredit.repository.LoanSchemeRepository;
import com.paracredit.paracredit.results.*;
import jakarta.servlet.http.HttpServletRequest;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class HomeController {

    String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=AIzaSyDkIgAqUeOER3CJD5muh1oGLXJgxiSoP0Q";

    public HomeController(LoanProductRepository loanProductRepository, LoanSchemeRepository loanSchemeRepository) {
        this.loanProductRepository = loanProductRepository;
        this.loanSchemeRepository = loanSchemeRepository;
    }

    @GetMapping("/")
    public String home() {
        return "onboarding"; // This refers to src/main/resources/templates/index.html
    }



    @GetMapping("/onboarding")
    public String onboarding () {
        return "onboarding";
    }

    @Autowired
    private final LoanProductRepository loanProductRepository;

    @Autowired
    private final LoanSchemeRepository loanSchemeRepository;

    List<Requirement> requirementList = new ArrayList<>();
    List<Goal> goalList = new ArrayList<>();
    List<CreditRequirements> creditRequirementsList = new ArrayList<>();
    List<BusinessDetails>businessDetailsList = new ArrayList<>();

    @GetMapping("/compare")
    public String compare(Model model) {
        List<LoanScheme> loanSchemes = loanSchemeRepository.findAll();
        model.addAttribute("loanSchemes", loanSchemes);
        return "compare";
    }


    @PostMapping("/start-onboarding")
    public String startOnboarding(HttpServletRequest request, Model model) {
        String name = request.getParameter("name");
        String mobile = request.getParameter("mobile");
        String dob = request.getParameter("dob");
        String pan = request.getParameter("pan");
        String city = request.getParameter("city");
        String typeOfLoan = request.getParameter("loan-type");
        String businessName = request.getParameter("business-name");
        String businessConstitution = request.getParameter("business-constitution");
        String yearOfRegistration = request.getParameter("registration-year");
        String annualTurnover = request.getParameter("annual-turnover");
        String natureOfBusiness = request.getParameter("nature-of-business");
        String revenue = request.getParameter("revenue");
        String profitMargin = request.getParameter("profit-margin");
        String cashFlow = request.getParameter("cash-flow");
        String existingDebt = request.getParameter("existing-debt");
        String collateral = request.getParameter("collateral");
        String purpose = request.getParameter("purpose");
        String amountRequired = request.getParameter("amount-required");
        String repaymentCycle = request.getParameter("repayment-cycle");
        String riskAppetite = request.getParameter("risk-appetite");
        String flexibility = request.getParameter("flexibility");

        BusinessDetails businessDetails = new BusinessDetails(businessName, businessConstitution, yearOfRegistration, annualTurnover, natureOfBusiness, revenue, cashFlow, profitMargin, existingDebt, collateral);
        CreditRequirements creditRequirements = new CreditRequirements(purpose, amountRequired, repaymentCycle, riskAppetite, flexibility);
        User user = new User(name, mobile, "", dob, pan, city, typeOfLoan, businessDetails, creditRequirements);
        String userPrompt = user.toString();
        businessDetailsList.add(businessDetails);
        // extract requirements
        RequirementPrompt req = new RequirementPrompt();
        String reqPrompt = req.toPrompt(userPrompt);
        Requirement requirement = extractRequirement(reqPrompt);
        // extract goals
        GoalsPrompt goalPrompt = new GoalsPrompt();
        String goalString = goalPrompt.toPrompt(userPrompt);
        Goal goal = extractGoals(goalString);
        // save in cache (User, Requirements, Goals)
        System.out.println(userPrompt);
        System.out.println(requirement.getRequirement1());
        System.out.println(goal.getGoal1());

        requirementList.add(requirement);
        goalList.add(goal);
        creditRequirementsList.add(creditRequirements);
        List<LoanProduct> loanProducts = loanProductRepository.findAll();
        model.addAttribute("loanProducts", loanProducts);

        model.addAttribute("requirement", requirementList);
        model.addAttribute("goal", goalList);

        return "explore";
    }

    @GetMapping("/recommended")
    public String recommended(Model model) throws JsonProcessingException {
        List<LoanProduct> loanProducts = loanProductRepository.findAll();
        // get recommended products list
        List<LoanProduct> newList = recommendedProducts(loanProducts);
        model.addAttribute("loanProducts", newList);
        return "explore :: #comparison-result";

    }

    public List<LoanProduct> recommendedProducts(List<LoanProduct> productsList) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productsList);
        String creditDetailsJson = objectMapper.writeValueAsString(creditRequirementsList);
        String prompt = "You are a credit discovery platform for MSMEs. Following is the products json: " + "<productsJson>" + "\n"
+ json + "</productsJson>\n" + "Following is the credit details JSON " + "<creditJson>\n" + creditDetailsJson + "</creditJson>\n" +
                "Return the list of the top 5 recommended products from the products list based on the user's credit details required. Return strictly in the following JSON schema. Make sure the JSON syntax is correct and the schema is followed" + "\n"
                +  "\\n LoanProduct = {\\\\\\\"productType\\\\\\\": str, \\\\\\\"eligibilityCriteria\\\\\\\": str, \\\\\\\"maxLoanAmount\\\\\\\": str, \\\\\\\"interestRate\\\\\\\": str, \\\\\\\"processingFee\\\\\\\": str, \\\\\\\"maxTenure\\\\\\\": str, \\\\\\\"collateral\\\\\\\": str}\\n\" +\n" +
                "                \"            Return: list[LoanProduct]\"";
        List<LoanProduct> recommendedList = extractRecommendation(prompt);
        return recommendedList;
    }

    @GetMapping("/meets-requirements")
    public String meetsRequirements(Model model) throws JsonProcessingException {
        List<LoanProduct> loanProducts = loanProductRepository.findAll();
        // get filtered products list
        List<LoanProduct> newList = requiredProducts(loanProducts);
        model.addAttribute("loanProducts", newList);
        return "explore :: #comparison-result";
    }

    public List<LoanProduct> requiredProducts(List<LoanProduct> productList) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productList);
        String requirementsJson = objectMapper.writeValueAsString(requirementList);
        String prompt = "You are a credit discovery platform for MSMEs. Following is the products json: " + "<productsJson>" + "\n"
                + json + "</productsJson>\n" + "Following is the requirement JSON " + "<requirementJson>\n" + requirementsJson + "</requirementJson>\n" +
                "Return the list of the top 5 products that meet the user's requirements partially or completely from the products list based on the user's requirements provided. Return strictly in the following JSON schema. Make sure the JSON syntax is correct and the schema is followed" + "\n"
                +  "\\n LoanProduct = {\\\\\\\"productType\\\\\\\": str, \\\\\\\"eligibilityCriteria\\\\\\\": str, \\\\\\\"maxLoanAmount\\\\\\\": str, \\\\\\\"interestRate\\\\\\\": str, \\\\\\\"processingFee\\\\\\\": str, \\\\\\\"maxTenure\\\\\\\": str, \\\\\\\"collateral\\\\\\\": str}\\n\" +\n" +
                "                \"            Return: list[LoanProduct]\"";
        List<LoanProduct> recommendedList = extractRecommendation(prompt);
        for (LoanProduct loan: recommendedList) {
            loan.setTooltip("Meets Requirements");
        }
        return recommendedList;
    }

    @GetMapping("/meets-goals")
    public String meetsGoals(Model model) throws JsonProcessingException {
        List<LoanProduct> loanProducts = loanProductRepository.findAll();
        // get filtered products list
        List<LoanProduct> newList = goalProducts(loanProducts);
        model.addAttribute("loanProducts", newList);
        return "explore :: #comparison-result";
    }

    public List<LoanProduct> goalProducts(List<LoanProduct> productList) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productList);
        String requirementsJson = objectMapper.writeValueAsString(goalList);
        String prompt = "You are a credit discovery platform for MSMEs. Following is the products json: " + "<productsJson>" + "\n"
                + json + "</productsJson>\n" + "Following is the goals JSON " + "<goalsJson>\n" + requirementsJson + "</goalsJson>\n" +
                "Return the list of the top 5 products that meet the user's goals partially or completely from the products list based on the user's goals provided. Return strictly in the following JSON schema. Make sure the JSON syntax is correct and the schema is followed" + "\n"
                +  "\\n LoanProduct = {\\\\\\\"productType\\\\\\\": str, \\\\\\\"eligibilityCriteria\\\\\\\": str, \\\\\\\"maxLoanAmount\\\\\\\": str, \\\\\\\"interestRate\\\\\\\": str, \\\\\\\"processingFee\\\\\\\": str, \\\\\\\"maxTenure\\\\\\\": str, \\\\\\\"collateral\\\\\\\": str}\\n\" +\n" +
                "                \"            Return: list[LoanProduct]\"";
        List<LoanProduct> recommendedList = extractRecommendation(prompt);
        for (LoanProduct loan: recommendedList) {
            loan.setTooltip("Meets Goals");
        }
        return recommendedList;
    }


    public List<LoanProduct> extractRecommendation(String prompt) {
        RestTemplate restTemplate = new RestTemplate();

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject requestBody = new JSONObject();

        JSONObject generationConfig = new JSONObject();
        generationConfig.put("response_mime_type", "application/json");

        JSONObject textPart = new JSONObject();
        textPart.put("text", prompt);

        JSONObject parts = new JSONObject();
        parts.append("parts", textPart);

        requestBody.append("contents", parts);
        requestBody.put("generationConfig", generationConfig);
        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

        PromptResult promptResult = new PromptResult();
        List<LoanProduct> requirements = new ArrayList<>();


        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {

                JSONObject jsonObject = new JSONObject(response.getBody());
                JSONArray candidates = jsonObject.getJSONArray("candidates");


                for (int i = 0; i < candidates.length(); i++) {
                    JSONObject candidate = candidates.getJSONObject(i);
                    JSONObject content = candidate.getJSONObject("content");
                    JSONArray responseParts = content.getJSONArray("parts");

                    // Extract the 'text' from the first part
                    String text = responseParts.getJSONObject(0).getString("text");

                    List<Integer> chartData = new ArrayList<>();
                    // Parse the text as JSON to extract topics
                    JSONArray reqArray = new JSONArray(text);
                    for (int j = 0; j < reqArray.length(); j++) {
                        JSONObject reqJson = reqArray.getJSONObject(j);

                        // Create a Topic object from the JSON
                        LoanProduct requirement = new LoanProduct(
                                1l,
                                reqJson.getString("productType"),
                                reqJson.getString("eligibilityCriteria"),
                                reqJson.getString("maxLoanAmount"),
                                reqJson.getString("interestRate"),
                                reqJson.getString("processingFee"),
                                reqJson.getString("maxTenure"),
                                reqJson.getString("collateral")
                        );
                        requirement.setTooltip("Recommended");
                        requirements.add(requirement);
                    }

                }
            }

            else {
                System.out.println("Request failed with status code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requirements;
    }

    @PostMapping("/compareResult")
    public String compareCreditOptions(
            HttpServletRequest request,
            Model model) throws JsonProcessingException {

        String creditOption1 = request.getParameter("credit-option-1");
        String creditOption2 = request.getParameter("credit-option-2");

        ComparisonPrompt prompt = new ComparisonPrompt();
        prompt.setCreditOption1(creditOption1);
        prompt.setCreditOption2(creditOption2);

        ObjectMapper objectMapper = new ObjectMapper();
        String requirementJson = objectMapper.writeValueAsString(requirementList);
        String businessJson = objectMapper.writeValueAsString(creditRequirementsList);

        String promptString = prompt.toPrompt(requirementJson, businessJson);
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

        PromptResult promptResult = new PromptResult();

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {

                JSONObject jsonObject = new JSONObject(response.getBody());
                JSONArray candidates = jsonObject.getJSONArray("candidates");

                List<Comparison> comparisons = new ArrayList<>();

                for (int i = 0; i < candidates.length(); i++) {
                    JSONObject candidate = candidates.getJSONObject(i);
                    JSONObject content = candidate.getJSONObject("content");
                    JSONArray responseParts = content.getJSONArray("parts");

                    // Extract the 'text' from the first part
                    String text = responseParts.getJSONObject(0).getString("text");

                    List<Integer> chartData = new ArrayList<>();
                    // Parse the text as JSON to extract topics
                    JSONArray comparisonArray = new JSONArray(text);
                    for (int j = 0; j < comparisonArray.length(); j++) {
                        JSONObject comparisonJson = comparisonArray.getJSONObject(j);

                        // Create a Topic object from the JSON
                        Comparison comparison = new Comparison(
                                comparisonJson.getString("creditOption"),
                                comparisonJson.getString("loanTenure"),
                                comparisonJson.getString("creditScore"),
                                comparisonJson.getString("maxLoanAmount"),
                                comparisonJson.getString("interestRate"),
                                comparisonJson.getString("suitabilityScore"),
                                comparisonJson.getString("reasonForSuitability"),
                                comparisonJson.getString("eligibilityScore"),
                                comparisonJson.getString("reasonForEligibility")
                                );
                        comparisons.add(comparison);
                    }

                    promptResult.setComparisonList(comparisons);

                }

            }

            else {
                System.out.println("Request failed with status code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("comparisonList", promptResult.getComparisonList());
        System.out.println(promptString);
        // Return the fragment for HTMX to update the page
        return "comparison-result :: #comparison-result";
    }

    public Requirement extractRequirement(String promptString) {
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

        PromptResult promptResult = new PromptResult();
        List<Requirement> requirements = new ArrayList<>();


        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {

                JSONObject jsonObject = new JSONObject(response.getBody());
                JSONArray candidates = jsonObject.getJSONArray("candidates");


                for (int i = 0; i < candidates.length(); i++) {
                    JSONObject candidate = candidates.getJSONObject(i);
                    JSONObject content = candidate.getJSONObject("content");
                    JSONArray responseParts = content.getJSONArray("parts");

                    // Extract the 'text' from the first part
                    String text = responseParts.getJSONObject(0).getString("text");

                    List<Integer> chartData = new ArrayList<>();
                    // Parse the text as JSON to extract topics
                    JSONArray reqArray = new JSONArray(text);
                    for (int j = 0; j < reqArray.length(); j++) {
                        JSONObject reqJson = reqArray.getJSONObject(j);

                        // Create a Topic object from the JSON
                        Requirement requirement = new Requirement(
                                reqJson.getString("requirement1"),
                                reqJson.getString("requirement2"),
                                reqJson.getString("requirement3"),
                                reqJson.getString("requirement4"),
                                reqJson.getString("requirement5")
                        );
                        requirements.add(requirement);
                    }

                }
            }

            else {
                System.out.println("Request failed with status code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requirements.get(0);
    }

    public Goal extractGoals(String promptString) {
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

        PromptResult promptResult = new PromptResult();
        List<Goal> goals = new ArrayList<>();


        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {

                JSONObject jsonObject = new JSONObject(response.getBody());
                JSONArray candidates = jsonObject.getJSONArray("candidates");


                for (int i = 0; i < candidates.length(); i++) {
                    JSONObject candidate = candidates.getJSONObject(i);
                    JSONObject content = candidate.getJSONObject("content");
                    JSONArray responseParts = content.getJSONArray("parts");

                    // Extract the 'text' from the first part
                    String text = responseParts.getJSONObject(0).getString("text");

                    List<Integer> chartData = new ArrayList<>();
                    // Parse the text as JSON to extract topics
                    JSONArray reqArray = new JSONArray(text);
                    for (int j = 0; j < reqArray.length(); j++) {
                        JSONObject reqJson = reqArray.getJSONObject(j);

                        // Create a Topic object from the JSON
                        Goal goal = new Goal(
                                reqJson.getString("goal1"),
                                reqJson.getString("goal2"),
                                reqJson.getString("goal3"),
                                reqJson.getString("goal4"),
                                reqJson.getString("goal5")
                        );
                        goals.add(goal);
                    }

                }
            }

            else {
                System.out.println("Request failed with status code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return goals.get(0);
    }

    @GetMapping("/{productType}")
    public String productDetail(@PathVariable String productType, Model model) {
        List<LoanScheme> loanSchemes = loanSchemeRepository.findAll();
        List<LoanProduct> loanProducts = loanProductRepository.findAll();
        loanSchemes = loanSchemes.stream().filter(v -> v.getProductType().equalsIgnoreCase(productType)).toList();
        loanProducts = loanProducts.stream().filter(v -> v.getProductType().equalsIgnoreCase(productType)).toList();
        model.addAttribute("loanSchemes", loanSchemes);
        model.addAttribute("loanProducts", loanProducts);
        return "product-detail";
    }

    @GetMapping("/pathways")
    public String pathways(Model model) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String requirementJson = objectMapper.writeValueAsString(requirementList);
        String businessDetailsJson = objectMapper.writeValueAsString(businessDetailsList);
        String creditRequirementJson = objectMapper.writeValueAsString(goalList);

        PathwaysPrompt prompt = new PathwaysPrompt();
        String promptString = prompt.toPrompt(requirementJson,creditRequirementJson,businessDetailsJson);
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

        PromptResult promptResult = new PromptResult();
        List<Pathway> pathways = new ArrayList<>();


        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {

                JSONObject jsonObject = new JSONObject(response.getBody());
                JSONArray candidates = jsonObject.getJSONArray("candidates");


                for (int i = 0; i < candidates.length(); i++) {
                    JSONObject candidate = candidates.getJSONObject(i);
                    JSONObject content = candidate.getJSONObject("content");
                    JSONArray responseParts = content.getJSONArray("parts");

                    // Extract the 'text' from the first part
                    String text = responseParts.getJSONObject(0).getString("text");

                    List<Integer> chartData = new ArrayList<>();
                    // Parse the text as JSON to extract topics
                    JSONArray reqArray = new JSONArray(text);
                    for (int j = 0; j < reqArray.length(); j++) {
                        JSONObject reqJson = reqArray.getJSONObject(j);

                        // Create a Topic object from the JSON
                        Pathway goal = new Pathway(
                                reqJson.getString("loanToValueRatio"),
                                reqJson.getString("recommendedLoanAmount"),
                                reqJson.getString("recommendedLoanTenure"),
                                reqJson.getString("financialHealthPotentialImprovements"),
                                reqJson.getString("creditworthinessPotentialImprovements"),
                                reqJson.getString("feedback"),
                                reqJson.getString("expenseManagement"),
                                reqJson.getString("incomeStability"),
                                reqJson.getString("assetDiversification"),
                                reqJson.getString("creditHistoryLength"),
                                reqJson.getString("creditMix"),
                                reqJson.getString("deliquencyRisk")


                                );
                        pathways.add(goal);
                    }

                }
            }

            else {
                System.out.println("Request failed with status code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("pathwaysList", pathways);
        return "pathways";
    }

    @GetMapping("/explore")
    public String explore(Model model) {
        List<LoanProduct> loanProducts = loanProductRepository.findAll();
        model.addAttribute("loanProducts", loanProducts);

        model.addAttribute("requirement", requirementList);
        model.addAttribute("goal", goalList);
        return "explore";
    }
}
