package com.Mano.Ad.model;
import com.google.gson.annotations.SerializedName;



public class ad_code{
    @SerializedName("id")
    int id;
    @SerializedName("point")
    int point;
    @SerializedName("code")
    String code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
