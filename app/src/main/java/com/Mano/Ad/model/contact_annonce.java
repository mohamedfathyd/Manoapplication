package com.Mano.Ad.model;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class contact_annonce {
    @SerializedName("id")
    int id;

    @SerializedName("image")
    String Image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
