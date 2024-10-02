package com.paracredit.paracredit.loader;

import com.paracredit.paracredit.entity.LoanScheme;
import com.paracredit.paracredit.repository.LoanSchemeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.paracredit.paracredit.repository.LoanProductRepository;
import com.paracredit.paracredit.entity.LoanProduct;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final LoanProductRepository repository;
    private final LoanSchemeRepository loanSchemeRepository;

    public DatabaseLoader(LoanProductRepository repository, LoanSchemeRepository loanSchemeRepository) {
        this.repository = repository;
        this.loanSchemeRepository = loanSchemeRepository;
    }

    @Override
    public void run(String... args) {
        List<LoanProduct> products = Arrays.asList(
                new LoanProduct(1L, "Working Capital Demand Loan", "MSMEs, turnover-based eligibility", "Based on working capital needs", "8.5% - 12% p.a.", "Up to 1% of loan amount", "1 year, renewable", "Collateral varies"),
                new LoanProduct(2L, "Cash Credit", "MSMEs with business accounts", "Up to ₹5 crore", "9% - 13% p.a.", "Up to 2% of loan amount", "1 year, renewable", "Asset-backed, sometimes unsecured"),
                new LoanProduct(3L, "Overdraft", "MSMEs with an operational account", "Up to ₹5 crore", "9% - 13% p.a.", "Up to 1% of loan amount", "1 year, renewable", "Secured by business assets"),
                new LoanProduct(4L, "Short term loan", "MSMEs with stable business track", "₹50 lakh - ₹5 crore", "9% - 14% p.a.", "1% to 2% of loan amount", "Up to 3 years", "Secured or unsecured"),
                new LoanProduct(5L, "Discounting", "MSMEs with valid trade bills", "Up to ₹2 crore", "8.2% - 13% p.a.", "0.25%-1%", "Depends on bill tenure", "Asset-backed"),
                new LoanProduct(6L, "Sales Bill discounting", "MSMEs with confirmed sales orders", "Based on bill amount", "8.2% - 13% p.a.", "0.25% to 1% of bill value", "Based on bill cycle", "No collateral in most cases"),
                new LoanProduct(7L, "Purchase bill discounting", "MSMEs with pending payment from buyers", "Based on purchase bill amount", "8.2% - 13% p.a.", "0.25% to 1%", "Based on bill cycle", "Generally no collateral"),
                new LoanProduct(8L, "Letter of credit", "Exporting MSMEs", "Linked to trade cycle", "Varies", "Up to 2%", "Linked to credit limit", "Collateral-backed"),
                new LoanProduct(9L, "Bank guarantee", "MSMEs participating in tenders or contracts", "Linked to project requirements", "8.2% - 13% p.a.", "0.5% to 2%", "Based on project duration", "Asset-backed"),
                new LoanProduct(10L, "Vendor financing", "Vendors to large corporations", "₹25 lakh - ₹5 crore", "9% - 13% p.a.", "Up to 1%", "Linked to supply agreement", "Secured"),
                new LoanProduct(11L, "Dealer financing", "MSMEs who are distributors of large corporates", "₹25 lakh - ₹5 crore", "9% - 12.5% p.a.", "Up to 1%", "Linked to agreement", "Generally unsecured"),
                new LoanProduct(12L, "Factoring", "MSMEs with receivables", "Up to ₹2 crore", "Linked to receivables", "0.5% to 1%", "Based on receivable period", "Secured by invoices"),
                new LoanProduct(13L, "Reverse Factoring", "Suppliers to large buyers", "Based on buyer agreement", "Linked to receivables", "0.5% to 1%", "Based on receivable period", "No collateral required"),
                new LoanProduct(14L, "Term Loan", "MSMEs requiring capital expenditure", "₹10 lakh - ₹5 crore", "8.75% - 12.5% p.a.", "0.5% to 2%", "Up to 7 years", "Asset-backed or secured"),
                new LoanProduct(15L, "Construction financing", "MSMEs in construction or related industries", "₹50 lakh - ₹10 crore", "9.5% - 13% p.a.", "1% of loan amount", "1 to 10 years", "Asset-backed"),
                new LoanProduct(16L, "Green Field financing", "New MSMEs setting up projects", "₹50 lakh - ₹5 crore", "8.5% - 12.5% p.a.", "1% to 2%", "5 to 7 years", "Asset-backed"),
                new LoanProduct(17L, "Loan against property", "MSMEs with immovable property", "Up to ₹10 crore", "9% - 12% p.a.", "Up to 1%", "7 to 15 years", "Property collateral"),
                new LoanProduct(18L, "Lease rental discounting", "MSMEs with steady rental income", "₹50 lakh - ₹10 crore", "8% - 12% p.a.", "0.5% to 1%", "7 to 10 years", "Secured by rental property"),
                new LoanProduct(19L, "Inventory funding", "MSMEs with significant inventory", "Up to ₹2 crore", "9% - 13% p.a.", "0.25% to 1%", "Renewable yearly", "Asset-backed"),
                new LoanProduct(20L, "Export packing credit", "MSMEs engaged in exports", "₹25 lakh - ₹10 crore", "8.2% - 13% p.a.", "0.25% to 1%", "180-360 days", "Collateralized or export bills"),
                new LoanProduct(21L, "Packing credit in Foreign currency", "MSMEs engaged in exports", "Linked to export order", "LIBOR linked", "Up to 1%", "180-360 days", "Export receivables"),
                new LoanProduct(22L, "Micro-Credit Scheme", "Micro-enterprises", "Up to ₹10 lakh", "10% - 14% p.a.", "Up to 1%", "1-5 years", "Secured or unsecured"),
                new LoanProduct(23L, "Credit for Micro Finance", "MFIs providing loans to MSMEs", "₹10 lakh - ₹1 crore", "9% - 14% p.a.", "1% of loan amount", "1-3 years", "Collateral-backed"),
                new LoanProduct(24L, "Refinance Scheme for Banks", "Banks lending to MSMEs", "Linked to bank exposure", "Linked to policy rates", "Varies", "As per agreement", "Secured"),
                new LoanProduct(25L, "Mahila Uddyami Scheme", "Women entrepreneurs in MSMEs", "₹10 lakh - ₹1 crore", "9% - 12% p.a.", "Up to 1%", "1-5 years", "Unsecured or lightly secured"),
                new LoanProduct(26L, "MUDRA Card", "Micro-entrepreneurs under Pradhan Mantri Mudra Yojana", "₹50,000 to ₹10 lakh", "8.4% - 12% p.a.", "No processing fee", "1-5 years", "Unsecured")
        );

        String fileName = "src/main/resources/loanSchemes.csv";
        loadCSVData(fileName);

        repository.saveAll(products);
    }
    private void loadCSVData(String fileName) {
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            List<String[]> rows = reader.readAll();

            for (String[] row : rows) {
                if (row[0].equalsIgnoreCase("Sl No.")) continue; // Skip header row

                LoanScheme loanScheme = new LoanScheme();
                loanScheme.setBankName(row[1]);
                loanScheme.setSchemeName(row[2]);
                loanScheme.setProductType(row[3]);
                loanScheme.setLoanAmount(row[4]);
                loanScheme.setTenure(row[5]);
                loanScheme.setInterestRate(row[6]);
                loanScheme.setProcessingFee(row[7]);
                loanScheme.setCollateral(row[8]);

                loanSchemeRepository.save(loanScheme);
            }

            System.out.println("CSV Data Loaded Successfully!");
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
}
