package com.git.ravaee.tezpishir.root;

import com.git.ravaee.tezpishir.api.apiService.AuthApiService;
import com.git.ravaee.tezpishir.api.apiService.FoodApiService;
import com.git.ravaee.tezpishir.api.apiService.GroupApiService;
import com.git.ravaee.tezpishir.api.apiService.UserApiService;
import com.git.ravaee.tezpishir.utility.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    private Retrofit retrofit = null;

    @Provides
    @Singleton
    Gson provideGson(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return gson;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson){

        if (retrofit==null) {
            OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
            okhttpClientBuilder.connectTimeout(8, TimeUnit.SECONDS);
            okhttpClientBuilder.readTimeout(8, TimeUnit.SECONDS);
            okhttpClientBuilder.writeTimeout(8, TimeUnit.SECONDS);

            OkHttpClient okHttp = okhttpClientBuilder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.SERVER_API_URL())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttp)
                    .build();
        }
        return retrofit;
    }

    @Provides
    @Singleton
    GroupApiService provideGroupApiService(Gson gson, Retrofit retrofit){

        GroupApiService groupApiService = new GroupApiService(gson,retrofit);
        return  groupApiService;
    }

    @Provides
    @Singleton
    AuthApiService provideAuthApiService(Gson gson, Retrofit retrofit){

        return new AuthApiService(gson,retrofit);
    }

    @Provides
    @Singleton
    FoodApiService provideFoodApiService(Gson gson, Retrofit retrofit){

        return new FoodApiService(gson,retrofit);
    }

    @Provides
    @Singleton
    UserApiService provideUserApiService(Gson gson, Retrofit retrofit){

        return new UserApiService(gson,retrofit);
    }


}
