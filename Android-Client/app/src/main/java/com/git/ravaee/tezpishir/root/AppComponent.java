package com.git.ravaee.tezpishir.root;

import com.git.ravaee.tezpishir.api.apiService.AuthApiService;
import com.git.ravaee.tezpishir.api.apiService.FoodApiService;
import com.git.ravaee.tezpishir.api.apiService.GroupApiService;
import com.git.ravaee.tezpishir.api.apiService.UserApiService;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    GroupApiService GROUP_API_SERVICE();
    AuthApiService AUTH_API_SERVICE();
    FoodApiService FOOD_API_SERVICE();
    UserApiService USER_API_SERVICE();
}
