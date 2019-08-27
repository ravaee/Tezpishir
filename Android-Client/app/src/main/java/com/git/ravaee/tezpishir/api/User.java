package com.git.ravaee.tezpishir.api;

import com.git.ravaee.tezpishir.model.response.UserResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

public interface User {

    @GET("user/getMyProfile")
    Observable<UserResponse> getUser(@Header("x-access-token") String token);


    @Multipart
    @POST("user/editMyProfile")
    Observable<UserResponse> editUser (@Header("x-access-token") String token,
                                       @Part MultipartBody.Part file,
                                       @Part MultipartBody.Part fullName,
                                       @Part MultipartBody.Part experience,
                                       @Part MultipartBody.Part areaOfService,
                                       @Part MultipartBody.Part phoneNumber,
                                       @Part MultipartBody.Part id);

}
