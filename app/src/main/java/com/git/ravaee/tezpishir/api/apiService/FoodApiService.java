package com.git.ravaee.tezpishir.api.apiService;

import com.git.ravaee.tezpishir.api.Food;
import com.git.ravaee.tezpishir.model.response.FoodResponse;
import com.google.gson.Gson;
import retrofit2.Retrofit;
import rx.Observable;

public class FoodApiService extends ApiService{

    public FoodApiService(Gson gson, Retrofit retrofit) {
        super(gson, retrofit);
    }

    public Observable<FoodResponse> getFoodList(String token,String groupId,int pageNumber, int count){
        Food foodApi = retrofit.create(Food.class);
        return foodApi.list(token,groupId,pageNumber,count);
    }

    public Observable<FoodResponse> getSingleFood(String token,String foodId){
        Food foodApi = retrofit.create(Food.class);
        return foodApi.single(token,foodId);
    }
}
