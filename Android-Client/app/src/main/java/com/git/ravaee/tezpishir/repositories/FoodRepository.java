package com.git.ravaee.tezpishir.repositories;

import android.util.Log;
import android.widget.Toast;
import androidx.lifecycle.MutableLiveData;
import com.git.ravaee.tezpishir.model.Food;
import com.git.ravaee.tezpishir.model.Group;
import com.git.ravaee.tezpishir.model.response.FoodResponse;
import com.git.ravaee.tezpishir.root.App;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FoodRepository {

    protected App app;
    private static final String TAG = "FOOD_REPOSITORY";

    public FoodRepository(App app) {
        this.app = app;
    }


    public MutableLiveData<FoodResponse> getFoodList(Group group, int pageNumber, int count){

        final MutableLiveData<FoodResponse> foodResponseLiveData = new MutableLiveData<>();

        app.getFoodService().getFoodList(app.getSession().getToken(),group.getId(),pageNumber,count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FoodResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(app.getApplicationContext(), "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(FoodResponse foodResponse) {

                        foodResponseLiveData.setValue(foodResponse);
                    }
                });

        return  foodResponseLiveData;

    }

    public MutableLiveData<FoodResponse> getFoodDetails(Food food){

        final MutableLiveData<FoodResponse> foodResponseMutableLiveData = new MutableLiveData<>();

        app.getFoodService().getSingleFood(app.getSession().getToken(),food.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FoodResponse>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Unable to submit post to API." + e);
                        Toast.makeText(app.getApplicationContext(), "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(FoodResponse foodResponse) {
                        foodResponseMutableLiveData.setValue(foodResponse);
                    }
                });

        return  foodResponseMutableLiveData;
    }
}
