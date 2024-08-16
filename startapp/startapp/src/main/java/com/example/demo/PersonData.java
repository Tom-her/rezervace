package com.example.demo;

import java.time.LocalDate;

public class PersonData {

    private Long idOsoby;
    private String jmeno;
    private String prijmeni;
    private String titul;
    private String rodneCislo;
    private LocalDate datumOd;
    private LocalDate datumDo;

    // Gettery a settery
    public Long getIdOsoby() {
        return idOsoby;
    }

    public void setIdOsoby(Long idOsoby) {
        this.idOsoby = idOsoby;
    }

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

    public String getTitul() {
        return titul;
    }

    public void setTitul(String titul) {
        this.titul = titul;
    }

    public String getRodneCislo() {
        return rodneCislo;
    }

    public void setRodneCislo(String rodneCislo) {
        this.rodneCislo = rodneCislo;
    }

    public LocalDate getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(LocalDate datumOd) {
        this.datumOd = datumOd;
    }

    public LocalDate getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(LocalDate datumDo) {
        this.datumDo = datumDo;
    }
}
