package xyz.eventstreamer.eventstreamer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Event extends BaseResponse {

    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("naziv")
    @Expose
    private String naziv;
    @SerializedName("opis")
    @Expose
    private String opis;
    @SerializedName("datum")
    @Expose
    private long datum;
    @SerializedName("objava")
    @Expose
    private List<Post> objava;
    @SerializedName("kategorija")
    @Expose
    private List<Category> kategorija;
    @SerializedName("lokacija")
    @Expose
    private List<Location> lokacija;
    @SerializedName("hashtag")
    @Expose
    private List<Hashtag> hashtag;
    @SerializedName("id_uporabnik")
    @Expose
    private String id_uporabnik;

    public Event() {}

    public String getIdDogodek() {
        return _id;
    }

    public void setIdDogodek(String _id) {
        this._id = _id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public long getDatum() {
        return datum;
    }

    public void setDatum(long datum) {
        this.datum = datum;
    }

    public List<Post> getObjava() {
        return objava;
    }

    public void setObjava(List<Post> objava) {
        this.objava = objava;
    }

    public List<Category> getKategorija() {
        return kategorija;
    }

    public void setKategorija(List<Category> kategorija) {
        this.kategorija = kategorija;
    }

    public List<Location> getLokacija() {
        return lokacija;
    }

    public void setLokacija(List<Location> lokacija) {
        this.lokacija = lokacija;
    }

    public List<Hashtag> getHashtag() {
        return hashtag;
    }

    public void setHashtag(List<Hashtag> hashtag) {
        this.hashtag = hashtag;
    }

    public String getIdUuporabnik() {
        return id_uporabnik;
    }

    public void setIdUporabnik(String id_uporabnik) {
        this.id_uporabnik = id_uporabnik;
    }
}
