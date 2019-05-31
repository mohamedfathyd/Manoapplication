package com.Mano.Ad.model;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class contact_second_home_realm  extends RealmObject {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("nameca")
    String nameca;
    @SerializedName("image")
    String Img;
   @SerializedName("point")
   int point;
   @SerializedName("time")
   int time;
   @SerializedName("text")
   String text;

    public String getNameca() {
        return nameca;
    }

    public void setNameca(String nameca) {
        this.nameca = nameca;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }
}
