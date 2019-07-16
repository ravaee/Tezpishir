package com.git.ravaee.tezpishir.root;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.git.ravaee.tezpishir.api.apiService.AuthApiService;
import com.git.ravaee.tezpishir.api.apiService.FoodApiService;
import com.git.ravaee.tezpishir.api.apiService.GroupApiService;
import com.git.ravaee.tezpishir.api.apiService.UserApiService;
import com.git.ravaee.tezpishir.utility.Session;

public class App extends Application {

    private Session session;
    private static App context;

    //Component
    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule())
                .build();
    }

    public GroupApiService getGroupService(){

        return appComponent.GROUP_API_SERVICE();
    }

    public FoodApiService getFoodService(){

        return appComponent.FOOD_API_SERVICE();
    }

    public AuthApiService getAuthService(){

        return appComponent.AUTH_API_SERVICE();
    }

    public UserApiService getUserService(){

        return appComponent.USER_API_SERVICE();
    }

    public static App getContext() {
        return context;
    }

    public Session getSession() {

        if (session == null) {

            session = new Session() {

                @Override
                public boolean isLoggedIn() {
                    return true;
                }

                @Override
                public void saveToken(String token) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("Setting",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Token",token);
                    editor.apply();
                }

                @Override
                public String getToken() {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("Setting",Context.MODE_PRIVATE);
                    return sharedPreferences.getString("Token", "1");
                }

                @Override
                public void saveEmail(String email) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("Setting",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Email",email);
                    editor.apply();
                }


                @Override
                public String getEmail() {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("Setting",Context.MODE_PRIVATE);
                    return sharedPreferences.getString("Email", "1");
                }


                @Override
                public String getName() {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("Setting",Context.MODE_PRIVATE);
                    return sharedPreferences.getString("Name", "1");
                }

                @Override
                public void saveName(String name) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("Setting",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Name",name);
                    editor.apply();
                }


                @Override
                public void saveImage(String image) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("Setting",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Image",image);
                    editor.apply();
                }

                @Override
                public String getImage() {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("Setting",Context.MODE_PRIVATE);
                    return sharedPreferences.getString("Image", "1");
                }
            };
        }

        return session;
    }

}