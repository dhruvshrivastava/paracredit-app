package com.paracredit.paracredit.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UniversityController {

    @GetMapping("/university/index")
    public String home() {
        return "university-modules";
    }

    @GetMapping("/university/modules")
    public String modules() {
        return "university-modules";
    }

}
