package com.git.ravaee.tezpishir.api.apiService;

import com.git.ravaee.tezpishir.api.Group;
import com.git.ravaee.tezpishir.api.User;
import com.git.ravaee.tezpishir.model.response.GroupResponse;
import com.git.ravaee.tezpishir.model.response.UserResponse;
import com.google.gson.Gson;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    public Observable<UserResponse> editUser(String token,
                                             MultipartBody.Part fullName,
                                             MultipartBody.Part phoneNumber,
                                             MultipartBody.Part experience,
                                             MultipartBody.Part areaOfService,
                                             MultipartBody.Part file,
                                             MultipartBody.Part id){

        User userApi = retrofit.create(User.class);
        return userApi.editUser(token,file,fullName,experience,areaOfService,phoneNumber,id);
    }
}
