package com.git.ravaee.tezpishir.api;

import com.git.ravaee.tezpishir.model.response.UserResponse;
import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

public interface User {

    @GET("user/getMyProfile")
    Observable<UserResponse> getUser(@Header("x-access-token") String token);
}
