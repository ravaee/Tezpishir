package com.git.ravaee.tezpishir.activity;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.git.ravaee.tezpishir.R;
import com.git.ravaee.tezpishir.model.User;
import com.git.ravaee.tezpishir.model.response.SignInResponse;
import com.git.ravaee.tezpishir.model.response.UserResponse;
import com.git.ravaee.tezpishir.root.App;

public class UserEditActivity extends AppCompatActivity {

    private static final String TAG = "SIGNIN_ACTIVITY";
    App app = null;
    User user;

    @BindView(R.id.user_name_text)
    EditText name_text;

    @BindView(R.id.phone_number_text)
    EditText phone_number_text;

    @BindView(R.id.experience_text)
    EditText experience_text;

    @BindView(R.id.area_of_service_text)
    EditText area_serice_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        app = (App)this.getApplication();
        ButterKnife.bind(this);

        getUser();
    }

    private void updateUI(){
        name_text.setText(user.getNickName());
        phone_number_text.setText("09330362920");
        experience_text.setText(user.getExperience());
        area_serice_text.setText(user.getAreaOfService());
    }

    private void getUser(){

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
                        user = userResponse.getUser();
                        updateUI();
                    }
                });
    }
}
