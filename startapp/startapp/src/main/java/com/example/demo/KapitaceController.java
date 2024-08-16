package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
    public class KapitaceController {

        @GetMapping("/kapitace")
        public String kapitacePage(Model model) {
            // Můžete přidat další atributy do modelu, pokud je potřebujete
            return "kapitace";
        }
    }

