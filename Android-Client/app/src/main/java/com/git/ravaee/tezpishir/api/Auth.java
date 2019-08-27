package com.git.ravaee.tezpishir.api;

import com.git.ravaee.tezpishir.model.response.SignInResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface Auth {

    @POST("auth/SignInWithGoogle")
    @FormUrlEncoded
    Observable<SignInResponse> SignInAndGetJWToken(@Field("id_token") String id_token,
                                                   @Field("email") String email);

    @POST("auth/login")
    @FormUrlEncoded
    Call<SignInResponse> SignInWithPasswordAndGetJWToken(@Field("email") String email,
                                                         @Field("password") String password);
}
