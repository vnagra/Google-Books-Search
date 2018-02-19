package com.vijendernagra.googlebookssearch.models.pojo;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

/**
 * Created by vijendernagra on 2/16/18.
 */

@Parcel
public class ImageLinks {

    private transient int id;

    @SerializedName("smallThumbnail")
    private String smallThumbnail;

    @SerializedName("thumbnail")
    private String thumbnail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSmallThumbnail() {
        return smallThumbnail;
    }

    public void setSmallThumbnail(String smallThumbnail) {
        this.smallThumbnail = smallThumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}

