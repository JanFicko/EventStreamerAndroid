package xyz.eventstreamer.eventstreamer.model.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "post")
public class PostEntity {

    @PrimaryKey
    @NonNull
    private String _id;
    private String id_uporabnik;
    private String komentar;
    private String slika;
    private String datum;
    private String id_dogodek;

    public PostEntity(String _id, String id_uporabnik, String komentar, String slika, String datum, String id_dogodek) {
        this._id = _id;
        this.id_uporabnik = id_uporabnik;
        this.komentar = komentar;
        this.slika = slika;
        this.datum = datum;
        this.id_dogodek = id_dogodek;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getId_uporabnik() {
        return id_uporabnik;
    }

    public void setId_uporabnik(String id_uporabnik) {
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

    public String getId_dogodek() {
        return id_dogodek;
    }

    public void setId_dogodek(String id_dogodek) {
        this.id_dogodek = id_dogodek;
    }
}
