package com.git.ravaee.tezpishir.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.git.ravaee.tezpishir.R;
import com.git.ravaee.tezpishir.adapter.food.DaggerFoodAdapterComponent;
import com.git.ravaee.tezpishir.adapter.food.FoodAdapter;
import com.git.ravaee.tezpishir.adapter.food.FoodAdapterComponent;
import com.git.ravaee.tezpishir.adapter.food.FoodAdapterModule;
import com.git.ravaee.tezpishir.model.Food;
import com.git.ravaee.tezpishir.model.Group;
import com.git.ravaee.tezpishir.model.response.FoodResponse;
import com.git.ravaee.tezpishir.root.App;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;

public class FoodsActivity extends AppCompatActivity {

    private static final String TAG = "FOODS_ACTIVITY";
    private App app = null;
    private FoodResponse.FoodBind foodBind = null;
    private Group group = null;
    private boolean isLoading = false;
    private int pageNumber = 1;

    @Inject
    FoodAdapter foodAdapter;

    protected RecyclerView.LayoutManager layoutManager;

    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;

    @BindView(R.id.refresh_layout)
    protected SwipeRefreshLayout refreshLayout;

    @BindView(R.id.toolbar_title)
    protected TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);
        app = (App)this.getApplication();
        ButterKnife.bind(this);

        refreshLayout.setOnRefreshListener(() -> getFoodList(group,1,10));

        String foodsAsJson = Objects.requireNonNull(getIntent().getExtras()).getString("Foods");
        String groupAsJson = Objects.requireNonNull(getIntent().getExtras()).getString("Group");

        this.foodBind = new Gson().fromJson(foodsAsJson, FoodResponse.FoodBind.class);
        this.group = new Gson().fromJson(groupAsJson, Group.class);

        title.setText(group.getName());

        updateUI();
    }

    @OnClick(R.id.back_button)
    protected void onBackButtonClicked(){
        finish();
    }

    private void foodDetails(Food food) {
        Intent intent = new Intent(this.app.getApplicationContext(),FoodActivity.class);
        String foodAsJson = new Gson().toJson(food);
        intent.putExtra("Food",foodAsJson);
        startActivity(intent);
    }

    private void updateUI(){

        isLoading = false;

        if(this.pageNumber == 1){
            FoodAdapterComponent foodAdapterComponent = DaggerFoodAdapterComponent.builder()
                    .foodAdapterModule(new FoodAdapterModule(this.foodBind.getFoods(), this.getApplicationContext(), this::foodDetails))
                    .build();

            foodAdapterComponent.inject(this);

            layoutManager = new LinearLayoutManager(this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(foodAdapter);
        }

        initScrollListener();
        foodAdapter.notifyDataSetChanged();
    }

    private void loadMore(int count) {

        if(count <= 0 || this.foodBind.getLeftOver() < 10){
            return;
        }

        foodBind.getFoods().add(null);
        foodAdapter.notifyItemInserted(foodBind.getFoods().size() - 1);

        getFoodList(this.group,this.pageNumber + 1, count);
    }

    private void initScrollListener() {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == foodBind.getFoods().size() - 1) {
                        int more = foodBind.getLeftOver() - (pageNumber * 10);
                        int count = (more > 10 ) ? 10 : (more >0) ? more : 0;

                        loadMore(count);
                        isLoading = true;
                    }
                }
            }
        });
    }


    private void getFoodList(Group group, int pageNumber, int count){

        this.pageNumber = pageNumber;

        app.getFoodService().getFoodList(app.getSession().getToken(),group.getId(),pageNumber,count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FoodResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        refreshLayout.setRefreshing(false);
                        Toast.makeText(app.getApplicationContext(), "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(FoodResponse foodResponse) {
                        refreshLayout.setRefreshing(false);

                        if(foodResponse.getErrors().size() > 0){
                            Toast.makeText(app.getApplicationContext(), foodResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(pageNumber > 1){
                            foodBind.getFoods().remove(foodBind.getFoods().size() - 1);
                            int scrollPosition = foodBind.getFoods().size();
                            foodAdapter.notifyItemRemoved(scrollPosition);

                            foodBind.getFoods().addAll(foodResponse.getFoodBind().getFoods());

                        }else{
                            foodBind = foodResponse.getFoodBind();
                        }
                        updateUI();
                    }
                });
    }

}
