package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class FileUploadController {

    private List<PersonData> fullData = new ArrayList<>(); // Uložená data z prvního souboru
    private List<SimplePersonData> simpleData = new ArrayList<>(); // Uložená data z druhého souboru

    @GetMapping("/upload")
    public String uploadForm(Model model) {
        return "upload"; // Název HTML šablony
    }

    @PostMapping("/upload/full")
    public String handleFullFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        fullData.clear(); // Vyčistíme předchozí data
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy"); // Formát pro datum ve formátu "dd.MM.yyyy"

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            // Předpokládáme, že první řádek obsahuje záhlaví a vynecháme ho
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");

                PersonData personData = new PersonData();
                personData.setIdOsoby(parseLong(values.length > 0 ? values[0].trim() : ""));
                personData.setJmeno(values.length > 1 ? parseString(values[1].trim()) : "");
                personData.setPrijmeni(values.length > 2 ? parseString(values[2].trim()) : "");
                personData.setTitul(values.length > 3 ? parseString(values[3].trim()) : "");
                personData.setRodneCislo(values.length > 4 ? parseString(values[4].trim()) : "");
                personData.setDatumOd(values.length > 5 ? parseDate(values[5].trim(), dateFormatter) : null);
                personData.setDatumDo(values.length > 6 ? parseDate(values[6].trim(), dateFormatter) : null);

                fullData.add(personData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("data", fullData);
        return "upload"; // Název HTML šablony, aby se data zobrazila na stejné stránce
    }

    @PostMapping("/upload/simple")
    public String handleSimpleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        simpleData.clear(); // Vyčistíme předchozí data
        Map<String, Long> nameIdMap = new HashMap<>();

        // Naplníme mapu ID osob na základě jména a příjmení
        for (PersonData person : fullData) {
            if (person.getJmeno() != null && person.getPrijmeni() != null) {
                String key = person.getJmeno() + ";" + person.getPrijmeni();
                nameIdMap.put(key, person.getIdOsoby());
            }
        }

        List<SimplePersonData> matchingData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            // Předpokládáme, že první řádek obsahuje záhlaví a vynecháme ho
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");

                SimplePersonData simplePersonData = new SimplePersonData();
                simplePersonData.setJmeno(values.length > 0 ? parseString(values[0].trim()) : "");
                simplePersonData.setPrijmeni(values.length > 1 ? parseString(values[1].trim()) : "");

                String key = simplePersonData.getJmeno() + ";" + simplePersonData.getPrijmeni();
                Long id = nameIdMap.get(key);

                if (id != null) {
                    simplePersonData.setId(id);
                    matchingData.add(simplePersonData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("matchingData", matchingData);
        model.addAttribute("data", fullData); // Zobrazení všech dat ze souboru 1
        return "upload"; // Název HTML šablony, aby se data zobrazila na stejné stránce
    }

    // Metoda pro parsování Long s prázdnou hodnotou
    private Long parseLong(String value) {
        try {
            return value.isEmpty() ? null : Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // Metoda pro parsování LocalDate s prázdnou hodnotou
    private LocalDate parseDate(String value, DateTimeFormatter formatter) {
        try {
            return value.isEmpty() ? null : LocalDate.parse(value, formatter);
        } catch (Exception e) {
            return null;
        }
    }

    // Metoda pro parsování String s prázdnou hodnotou
    private String parseString(String value) {
        return value.isEmpty() ? null : value;
    }
}