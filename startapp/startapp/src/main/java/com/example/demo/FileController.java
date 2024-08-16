package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@Controller
public class FileController {

    @GetMapping("/read-file")
    public String readFile(@RequestParam(name = "filePath", required = false, defaultValue = "c:/temp/data.txt") String filePath, Model model) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            model.addAttribute("lines", lines);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error reading file: " + e.getMessage());
        }
        return "read-file";
    }
}

