package xyz.eventstreamer.eventstreamer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ime")
    @Expose
    private String ime;
    @SerializedName("priimek")
    @Expose
    private String priimek;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("geslo")
    @Expose
    private String geslo;
    @SerializedName("tip")
    @Expose
    private String tip;
    @SerializedName("medij")
    @Expose
    private String medij;
    @SerializedName("kategorija")
    @Expose
    private List<Category> kategorija = null;

    public User(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGeslo() {
        return geslo;
    }

    public void setGeslo(String geslo) {
        this.geslo = geslo;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getMedij() {
        return medij;
    }

    public void setMedij(String medij) {
        this.medij = medij;
    }

    public List<Category> getKategorija() {
        return kategorija;
    }

    public void setKategorija(List<Category> kategorija) {
        this.kategorija = kategorija;
    }
}
