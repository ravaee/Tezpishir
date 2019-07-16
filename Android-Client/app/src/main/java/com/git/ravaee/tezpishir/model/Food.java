package com.git.ravaee.tezpishir.model;

import com.git.ravaee.tezpishir.utility.Config;
import com.google.gson.annotations.SerializedName;

public class Food {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("time")
    private String time;

    @SerializedName("score")
    private int score;

    @SerializedName("owner")
    private User user;

    @SerializedName("image")
    private String image;

    @SerializedName("ingredients")
    private String ingredients;

    @SerializedName("recipes")
    private String recipes;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getImage() {
        return Config.SERVER_PUBLIC_URL() + "uploads/images/food/" + this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecipes() {
        return recipes;
    }

    public void setRecipes(String recipes) {
        this.recipes = recipes;
    }
}
