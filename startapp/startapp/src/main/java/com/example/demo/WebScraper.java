package com.example.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebScraper {

    public static void main(String[] args) {
        try {
            // Stáhneme HTML obsah z www.akcie.cz
            Document doc = Jsoup.connect("https://www.akcie.cz").get();

            // Vypíšeme název stránky
            System.out.println("Title: " + doc.title());

            // Najdeme všechny prvky, které nás zajímají
            // Například všechny odkazy na hlavní stránce
            Elements links = doc.select("a");

            for (Element link : links) {
                // Vypíšeme text a URL odkazu
                System.out.println("Text: " + link.text());
                System.out.println("URL: " + link.attr("href"));
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
