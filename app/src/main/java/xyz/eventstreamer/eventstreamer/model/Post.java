package xyz.eventstreamer.eventstreamer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Post extends BaseResponse {

    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("id_uporabnik")
    @Expose
    private String id_uporabnik;
    @SerializedName("komentar")
    @Expose
    private String komentar;
    @SerializedName("slika")
    @Expose
    private String slika;
    @SerializedName("datum")
    @Expose
    private String datum;
    @SerializedName("like")
    @Expose
    private List<Like> like;

    public Post() {}

    public String getIdObjava() {
        return _id;
    }

    public void setIdObjava(String _id) {
        this._id = _id;
    }

    public String getIdUporabnik() {
        return id_uporabnik;
    }

    public void setIdUporabnik(String id_uporabnik) {
        this.id_uporabnik = id_uporabnik;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public List<Like> getLike() {
        return like;
    }

    public void setLike(List<Like> like) {
        this.like = like;
    }
}
