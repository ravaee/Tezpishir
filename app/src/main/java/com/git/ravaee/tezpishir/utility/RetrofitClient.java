package com.git.ravaee.tezpishir.utility;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {

            OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
            okhttpClientBuilder.connectTimeout(8, TimeUnit.SECONDS);
            okhttpClientBuilder.readTimeout(8, TimeUnit.SECONDS);
            okhttpClientBuilder.writeTimeout(8, TimeUnit.SECONDS);

            OkHttpClient okHttp = okhttpClientBuilder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttp)
                    .build();
        }
        return retrofit;
    }
}