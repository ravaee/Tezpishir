package com.git.ravaee.tezpishir.api.apiService;

import com.git.ravaee.tezpishir.api.Auth;
import com.git.ravaee.tezpishir.model.response.SignInResponse;
import com.google.gson.Gson;

import retrofit2.Retrofit;
import rx.Observable;

public class AuthApiService extends ApiService{

    public AuthApiService(Gson gson, Retrofit retrofit) {
        super(gson, retrofit);
    }

    public Observable<SignInResponse> getUser(String tokenId, String email){
        Auth authApi = retrofit.create(Auth.class);
        Observable<SignInResponse> signInResponse = authApi.SignInAndGetJWToken(tokenId,email);
        return signInResponse;
    }
}
