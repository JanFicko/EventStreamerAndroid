package xyz.eventstreamer.eventstreamer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Hashtag extends BaseResponse implements Serializable {

    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("hashtag")
    @Expose
    private String hashtag;

    public Hashtag() {}

    public String getIdHashtag() {
        return _id;
    }

    public void setIdHashtag(String _id) {
        this._id = _id;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }
}
