package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @GetMapping("/logout-success")
    public String logoutSuccess() {
        return "logout-success"; // Vrací název šablony logout-success.html
    }
}