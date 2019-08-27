package com.git.ravaee.tezpishir.repositories;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import androidx.lifecycle.MutableLiveData;
import com.git.ravaee.tezpishir.model.response.UserResponse;
import com.git.ravaee.tezpishir.root.App;
import java.io.File;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserRepository {

    protected App app;
    private static final String TAG = "USER_REPOSITORY";

    public UserRepository(App app) {
        this.app = app;
    }

    public MutableLiveData<UserResponse> getUsers() {

        final MutableLiveData<UserResponse> userResponseLiveData = new MutableLiveData<>();

        app.getUserService().getUser(app.getSession().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserResponse>() {


                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Unable to submit post to API." + e);
                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        Log.i(TAG, "post submitted to API." + userResponse.getUser());
                        if(userResponse.getErrors().size() > 0){
                            Toast.makeText(app.getApplicationContext(), userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        userResponseLiveData.setValue(userResponse);

                    }
                });

        return userResponseLiveData;

    }

    public MutableLiveData<UserResponse> updateUser(String uid, String uphoneNumber, String uexperience, String uareaOfService, String ufullName, Uri uimageUri) {

        final MutableLiveData<UserResponse> userResponseLiveData = new MutableLiveData<>();

        MultipartBody.Part fbody = null;

        if(uimageUri != null){
            File file = new File(uimageUri.getPath());
            fbody = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        }

        MultipartBody.Part id = MultipartBody.Part.createFormData("id", uid);
        MultipartBody.Part name = MultipartBody.Part.createFormData("fullName", ufullName);
        MultipartBody.Part experience = MultipartBody.Part.createFormData("experience", uexperience);
        MultipartBody.Part phoneNumber = MultipartBody.Part.createFormData("phoneNumber", uphoneNumber);
        MultipartBody.Part areaOfService = MultipartBody.Part.createFormData("areaOfService", uareaOfService);


        app.getUserService().editUser(app.getSession().getToken(),
                name,phoneNumber,experience,areaOfService,fbody,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserResponse>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Unable to submit post to API." + e);
                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        Log.i(TAG, "post submitted to API." + userResponse.getUser());
                        if(userResponse.getErrors().size() > 0){
                            Toast.makeText(app.getApplicationContext(), userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        userResponseLiveData.setValue(userResponse);
                    }
                });

        return userResponseLiveData;

    }
}
