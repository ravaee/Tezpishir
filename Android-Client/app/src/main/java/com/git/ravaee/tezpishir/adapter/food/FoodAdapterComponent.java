package com.git.ravaee.tezpishir.adapter.food;

import com.git.ravaee.tezpishir.activity.FoodsActivity;
import javax.inject.Singleton;
import dagger.Component;

@Component(modules = {FoodAdapterModule.class})
@Singleton
public interface FoodAdapterComponent {
    void inject(FoodsActivity target);
}
