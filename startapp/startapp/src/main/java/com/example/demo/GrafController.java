package com.example.demo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


    @Controller
    public class GrafController {

        @GetMapping("/graf")
        public String showGrafForm(Model model) {
            return "graf";
        }

        @PostMapping("/graf")
        public String submitPrices(
                @RequestParam("price1") String price1,
                @RequestParam("price2") String price2,
                @RequestParam("price3") String price3,
                Model model) {
            model.addAttribute("price1", price1);
            model.addAttribute("price2", price2);
            model.addAttribute("price3", price3);
            return "grafResult";
        }
    }
