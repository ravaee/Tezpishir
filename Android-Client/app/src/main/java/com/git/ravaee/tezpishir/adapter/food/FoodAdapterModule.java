package com.git.ravaee.tezpishir.adapter.food;

import android.content.Context;
import com.git.ravaee.tezpishir.model.Food;
import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class FoodAdapterModule {

    private List<Food> dataset;
    private Context context;
    private FoodAdapter.OnItemClickListener onItemClickListener;

    public FoodAdapterModule(List<Food> dataset, Context context, FoodAdapter.OnItemClickListener onItemClickListener) {
        this.dataset = dataset;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Provides
    FoodAdapter provideGroupAdapter(){
        return new FoodAdapter(dataset,context,onItemClickListener);
    }
}
