package com.git.ravaee.tezpishir.model.response;

import com.git.ravaee.tezpishir.model.Food;
import com.git.ravaee.tezpishir.utility.Response;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FoodResponse extends Response {

    @SerializedName("models")
    private FoodBind foodBind;

    public FoodBind getFoodBind() {
        return this.foodBind;
    }

    public class FoodBind{

        @SerializedName("food")
        private Food food;

        @SerializedName("foods")
        private List<Food> foods;

        @SerializedName("leftOver")
        private int leftOver;

        public Food getFood() {
            return food;
        }

        public void setFood(Food food) {
            this.food = food;
        }

        public List<Food> getFoods() {
            return foods;
        }

        public void setFoods(List<Food> foods) {
            this.foods = foods;
        }

        public int getLeftOver() {
            return leftOver;
        }

        public void setLeftOver(int leftOver) {
            this.leftOver = leftOver;
        }
    }
}


