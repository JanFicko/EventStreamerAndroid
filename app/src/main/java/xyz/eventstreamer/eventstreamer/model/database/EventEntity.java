package xyz.eventstreamer.eventstreamer.model.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "event")
public class EventEntity {

    @PrimaryKey
    @NonNull
    private String _id;
    private String naziv;
    private String opis;
    private String datum;
    private String id_uporabnik;

    public EventEntity(String _id, String naziv, String opis, String datum, String id_uporabnik) {
        this._id = _id;
        this.naziv = naziv;
        this.opis = opis;
        this.datum = datum;
        this.id_uporabnik = id_uporabnik;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
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

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getId_uporabnik() {
        return id_uporabnik;
    }

    public void setId_uporabnik(String id_uporabnik) {
        this.id_uporabnik = id_uporabnik;
    }
}
