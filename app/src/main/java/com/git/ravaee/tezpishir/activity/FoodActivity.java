package com.git.ravaee.tezpishir.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.git.ravaee.tezpishir.model.Food;
import com.git.ravaee.tezpishir.model.response.FoodResponse;
import com.git.ravaee.tezpishir.root.App;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.git.ravaee.tezpishir.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class FoodActivity extends AppCompatActivity {

    private Food food;

    private static final String TAG = "FOODS_ACTIVITY";
    private App app = null;

    @BindView(R.id.image)
    protected ImageView food_image;

    @BindView(R.id.toolbar_title)
    protected TextView toolbar_title;

    @BindView(R.id.scroll_view)
    protected NestedScrollView scroll_view;

    @BindView(R.id.food_info_layout)
    protected RelativeLayout food_info_layout;

    @BindView(R.id.food_name)
    protected TextView food_name_text;

    @BindView(R.id.food_time)
    protected TextView food_time_text;

    @BindView(R.id.food_user_name)
    protected TextView food_user_name;

    @BindView(R.id.ingredient_text)
    protected TextView food_ingredients_text;

    @BindView(R.id.recipies_text)
    protected TextView food_recepies_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        app = (App)this.getApplication();

        String foodsAsJson = Objects.requireNonNull(getIntent().getExtras()).getString("Food");
        this.food = new Gson().fromJson(foodsAsJson, Food.class);

        updateStaticUI();

        getFoodDetails(this.food);
    }

    @SuppressLint("SetTextI18n")
    private void updateStaticUI(){

        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(" ");
        toolbar_title.setText(food.getName());

        scroll_view.getViewTreeObserver()
                .addOnScrollChangedListener(() -> {
                    if(scroll_view.getY() < 215){
                        toolbar_title.setVisibility(View.VISIBLE);
                    }else{
                        toolbar_title.setVisibility(View.GONE);
                    }
                });

        Picasso.with(app.getApplicationContext()).load(food.getImage())
                .into(food_image);

        food_name_text.setText(this.food.getName());
        food_user_name.setText(this.food.getUser().getNickName());
        food_time_text.setText(this.food.getTime() + " Min");
    }

    private void updateDynamicUI(Food food){

        this.food = food;
        food_ingredients_text.setText(food.getIngredients());
        food_recepies_text.setText(food.getRecipes());

    }


    void getFoodDetails(Food food){

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
                        if(foodResponse.getErrors().size() > 0){
                            Toast.makeText(app.getApplicationContext(), foodResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        updateDynamicUI(foodResponse.getFoodBind().getFood());
                    }
                });
    }




}
