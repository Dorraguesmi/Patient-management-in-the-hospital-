package model;

public class Patient {
    private int cin;
    private String nom;
    private String prenom;
    private String genre;
    private String telephone;
    private String adresse;

    public Patient(int cin, String nom, String prenom, String genre, String telephone, String adresse) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.genre = genre;
        this.telephone = telephone;
        this.adresse = adresse;
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
