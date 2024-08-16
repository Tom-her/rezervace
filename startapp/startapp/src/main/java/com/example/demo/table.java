package com.example.demo;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class table {


    private String[][] data1;
    private String[][] data2; // Druhá tabulka
    private String[][] data3; // Třetí tabulka
    private Map<String, Integer> columnMap;

    public table() {

        this.data1 = new String[][]{
                {"09:00-10:00", "", "", "", "", "", ""},
                {"10:00-11:00", "", "", "", "", "", ""},
                {"11:00-12:00", "", "", "", "", "", ""},
                {"12:00-13:00", "", "", "", "", "", ""},
                {"13:00-14:00", "", "", "", "", "", ""},
                {"14:00-15:00", "", "", "", "", "", ""},
                {"15:00-16:00", "", "", "", "", "", ""},
                {"17:00-18:00", "", "", "", "", "", ""},
                {"18:00-19:00", "", "", "", "", "", ""}
        };

        this.data2 = new String[][]{ // Inicializace druhé tabulky
                {"09:00-10:00", "", "", "", "", "", ""},
                {"10:00-11:00", "", "", "", "", "", ""},
                {"11:00-12:00", "", "", "", "", "", ""},
                {"12:00-13:00", "", "", "", "", "", ""},
                {"13:00-14:00", "", "", "", "", "", ""},
                {"14:00-15:00", "", "", "", "", "", ""},
                {"15:00-16:00", "", "", "", "", "", ""},
                {"17:00-18:00", "", "", "", "", "", ""},
                {"18:00-19:00", "", "", "", "", "", ""}
        };

        this.data3 = new String[][]{ // Inicializace druhé tabulky
                {"09:00-10:00", "", "", "", "", "", ""},
                {"10:00-11:00", "", "", "", "", "", ""},
                {"11:00-12:00", "", "", "", "", "", ""},
                {"12:00-13:00", "", "", "", "", "", ""},
                {"13:00-14:00", "", "", "", "", "", ""},
                {"14:00-15:00", "", "", "", "", "", ""},
                {"15:00-16:00", "", "", "", "", "", ""},
                {"17:00-18:00", "", "", "", "", "", ""},
                {"18:00-19:00", "", "", "", "", "", ""}
        };

        this.columnMap = new HashMap<>();
        this.columnMap.put("kurt1", 1);
        this.columnMap.put("kurt2", 2);
        this.columnMap.put("kurt3", 3);
        this.columnMap.put("kurt4", 4);
        this.columnMap.put("kurt5", 5);
        this.columnMap.put("kurt6", 6);
    }
    private static final Map<DayOfWeek, String> dayOfWeekMap = new HashMap<>();
    static {
        dayOfWeekMap.put(DayOfWeek.MONDAY, "Po");
        dayOfWeekMap.put(DayOfWeek.TUESDAY, "Út");
        dayOfWeekMap.put(DayOfWeek.WEDNESDAY, "St");
        dayOfWeekMap.put(DayOfWeek.THURSDAY, "Čt");
        dayOfWeekMap.put(DayOfWeek.FRIDAY, "Pá");
        dayOfWeekMap.put(DayOfWeek.SATURDAY, "So");
        dayOfWeekMap.put(DayOfWeek.SUNDAY, "Ne");
    }

    @GetMapping("/table")
    public String tablePage(Model model) {
        // Získání dnešního a zítřejšího data
        LocalDate today = LocalDate.now();
        LocalDate tomorrow1 = today.plusDays(1);
        LocalDate tomorrow2 = today.plusDays(2);

        // Formátování data na požadovaný formát (např. "dd.MM.yyyy")
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String todayFormatted = today.format(formatter);
        String tomorrowFormatted1 = tomorrow1.format(formatter);
        String tomorrowFormatted2 = tomorrow2.format(formatter);

        // Získání dne v týdnu a použití českých zkratek
        String todayDayOfWeek =     dayOfWeekMap.get(today.getDayOfWeek());
        String tomorrowDayOfWeek1 = dayOfWeekMap.get(tomorrow1.getDayOfWeek());
        String tomorrowDayOfWeek2 = dayOfWeekMap.get(tomorrow2.getDayOfWeek());

        // Přidání data a dne do modelu
        model.addAttribute("todayDate", todayFormatted);
        model.addAttribute("todayDayOfWeek", todayDayOfWeek);
        model.addAttribute("tomorrowDate1", tomorrowFormatted1);
        model.addAttribute("tomorrowDayOfWeek1", tomorrowDayOfWeek1);
        model.addAttribute("tomorrowDate2", tomorrowFormatted2);
        model.addAttribute("tomorrowDayOfWeek2", tomorrowDayOfWeek2);

        // Přidání dat z obou tabulek do modelu
        model.addAttribute("tableData1", data1);
        model.addAttribute("tableData2", data2);
        model.addAttribute("tableData3", data3);

        // Získání uživatelského jména
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Přidání uživatelského jména do modelu
        model.addAttribute("username", username);

        return "table"; // Vrací šablonu 'table.html'
    }


    @PostMapping("/update")
    public String updateTable(@RequestParam("date") String date,
                              @RequestParam("time") String time,
                              @RequestParam("column") String column,
                              @RequestParam("value") String value,
                              Model model) {
        // Parsing zadaného data
        LocalDate selectedDate = LocalDate.parse(date);
        LocalDate today = LocalDate.now();
        LocalDate tomorrow1 = today.plusDays(1);
        LocalDate tomorrow2 = today.plusDays(2);

        // Výběr správné tabulky na základě zadaného data
        String[][] dataToUpdate;
        if (selectedDate.equals(today)) {
            dataToUpdate = data1; // Aktualizace tabulky 1
        } else if (selectedDate.equals(tomorrow1)) {
            dataToUpdate = data2; // Aktualizace tabulky 2
        } else if (selectedDate.equals(tomorrow2)) {
            dataToUpdate = data3; // Aktualizace tabulky 3
        } else {
            model.addAttribute("error", "Zadané datum není platné. Můžete aktualizovat pouze dnešní, zítřejší a popozítřejší datum.");
            return "table"; // Zobrazí chybu na stejné stránce
        }

        // Nalezení správného řádku na základě času
        int rowIndex = -1;
        for (int i = 0; i < dataToUpdate.length; i++) {
            if (dataToUpdate[i][0].equals(time)) {
                rowIndex = i;
                break;
            }
        }

        // Kontrola, jestli řádek existuje a sloupec je platný
        if (rowIndex != -1 && columnMap.containsKey(column)) {
            int colIndex = columnMap.get(column);

            // Kontrola, jestli je buňka prázdná
            if (dataToUpdate[rowIndex][colIndex] == null || dataToUpdate[rowIndex][colIndex].isEmpty()) {
                // Pokud je buňka prázdná, vloží novou hodnotu
                dataToUpdate[rowIndex][colIndex] = value;
            } else {
                // Pokud buňka již obsahuje hodnotu, vrátí chybovou zprávu
                model.addAttribute("error", "Daný termín je již obsazen. Buňka již obsahuje hodnotu a nelze ji přepsat.");
                return "table"; // Zobrazí chybu na stejné stránce
            }
        }

        // Přesměrování zpět na stránku tabulky
        return "redirect:/table";
    }

    @PostMapping("/deleteAll")
    public String deleteAllEntries(Model model) {
        // Vymažte všechny záznamy
        for (int i = 0; i < data1.length; i++) {
            for (int j = 1; j < data1[i].length; j++) {
                data1[i][j] = ""; // Vymažte data v první tabulce
            }
        }

        for (int i = 0; i < data2.length; i++) {
            for (int j = 1; j < data2[i].length; j++) {
                data2[i][j] = ""; // Vymažte data ve druhé tabulce
            }
        }

        for (int i = 0; i < data3.length; i++) {
            for (int j = 1; j < data3[i].length; j++) {
                data3[i][j] = ""; // Vymažte data ve třetí tabulce
            }
        }

        // Po úspěšném smazání přesměrujte na stránku s tabulkou
        return "redirect:/table";
    }

    @GetMapping("/Web")
    public String getAkcie(Model model) {
        try {
            // Stáhneme HTML obsah z www.akcie.cz
            Document doc = Jsoup.connect("https://www.akcie.cz").get();

            // Získáme specifické informace ze stránky
            String title = doc.title();
            String headline = doc.select("h1").first().text(); // Například první <h1> na stránce

            // Přidáme získané informace do modelu
            model.addAttribute("title", title);
            model.addAttribute("headline", headline);

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Nepodařilo se stáhnout obsah stránky.");
        }

        return "Web";
    }
    @GetMapping("/film")
    public String filmPage(Model model) {
        // Přidáváme cestu k videu do modelu
        model.addAttribute("videoPath", "/videos/ct.mkv");
        return "film";
    }

    @GetMapping("/logout")
    public String logoutPage(Model model) {
        // Zde můžete přidat nějakou logiku, pokud je potřeba
        return "logout"; // Vrátí šablonu 'logout.html'
    }
    @GetMapping("/pavouk")
    public String showBracket(Model model) {
        return "bracket1";
    }
}