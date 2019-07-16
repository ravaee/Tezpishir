package com.git.ravaee.tezpishir.api.apiService;

import com.git.ravaee.tezpishir.api.Group;
import com.git.ravaee.tezpishir.api.User;
import com.git.ravaee.tezpishir.model.response.GroupResponse;
import com.git.ravaee.tezpishir.model.response.UserResponse;
import com.google.gson.Gson;

import retrofit2.Retrofit;
import rx.Observable;

public class UserApiService extends ApiService {

    public UserApiService(Gson gson, Retrofit retrofit) {
        super(gson, retrofit);
    }

    public Observable<UserResponse> getUser(String token){
        User userApi = retrofit.create(User.class);
        return userApi.getUser(token);
    }
}
