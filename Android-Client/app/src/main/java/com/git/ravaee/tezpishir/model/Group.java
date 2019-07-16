package com.git.ravaee.tezpishir.model;

import com.git.ravaee.tezpishir.utility.Config;
import com.google.gson.annotations.SerializedName;

public class Group {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("count")
    private int count;

    @SerializedName("image")
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCount() { return count; }

    public void setCount(int count) { this.count = count; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return Config.SERVER_PUBLIC_URL() + "uploads/images/group/" + this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
