package com.example.demo;

    public class SimplePersonData {
        private String jmeno;
        private String prijmeni;
        private Long id; // Přidáme ID

        // Gettery a settery
        public String getJmeno() {
            return jmeno;
        }

        public void setJmeno(String jmeno) {
            this.jmeno = jmeno;
        }

        public String getPrijmeni() {
            return prijmeni;
        }

        public void setPrijmeni(String prijmeni) {
            this.prijmeni = prijmeni;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }