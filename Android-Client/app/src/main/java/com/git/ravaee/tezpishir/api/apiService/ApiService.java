package com.git.ravaee.tezpishir.api.apiService;

import com.google.gson.Gson;
import retrofit2.Retrofit;

public class ApiService {
    Gson gson;
    Retrofit retrofit;

    public ApiService(Gson gson, Retrofit retrofit) {
        this.gson = gson;
        this.retrofit = retrofit;
    }
}
