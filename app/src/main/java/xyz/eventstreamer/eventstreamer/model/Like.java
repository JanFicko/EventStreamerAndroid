package xyz.eventstreamer.eventstreamer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Like extends BaseResponse {

    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("id_uporabnik")
    @Expose
    private String id_uporabnik;
    @SerializedName("like")
    @Expose
    private boolean like;

    public Like() {}

    public String getIdLike() {
        return _id;
    }

    public void setIdLike(String id_like) {
        this._id = _id;
    }

    public String getIdUporabnik() {
        return id_uporabnik;
    }

    public void setIdUporabnik(String id_uporabnik) {
        this.id_uporabnik = id_uporabnik;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }
}
