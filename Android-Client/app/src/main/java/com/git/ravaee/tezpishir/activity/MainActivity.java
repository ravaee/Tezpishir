package com.git.ravaee.tezpishir.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.git.ravaee.tezpishir.R;
import com.git.ravaee.tezpishir.model.response.SignInResponse;
import com.git.ravaee.tezpishir.utility.Apis;
import com.git.ravaee.tezpishir.root.App;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SIGNIN_ACTIVITY";
    private GoogleSignInClient mGoogleSignInClient;
    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (App)this.getApplication();

        Intent intent = new Intent(this, SigninActivity.class);
        startActivity(intent);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestServerAuthCode(getString(R.string.server_client_id),false)
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mGoogleSignInClient.silentSignIn()
                .addOnCompleteListener(
                        this,
                        new OnCompleteListener<GoogleSignInAccount>() {
                            @Override
                            public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                                handleSignInResult(task);
                            }
                        });

    }

    private void updateUI(SignInResponse response) {

        if(response == null){

            Intent intent = new Intent(this, SigninActivity.class);
            startActivity(intent);

        }else{

            Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, response.getUser().getId(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, response.getUser().getName(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, response.getUser().getEmail(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, response.getUser().getImage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, response.getUser().getToken(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            if(account != null){
                signInAndGetJWToken(account);
            }


        } catch (ApiException e) {

            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    void signInAndGetJWToken(final GoogleSignInAccount account){

        Apis.authApiService().SignInAndGetJWToken(account.getIdToken(), account.getEmail())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SignInResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Unable to submit post to API." + account.getIdToken());
                        updateUI(null);
                    }

                    @Override
                    public void onNext(SignInResponse signInResponse) {
                        Log.i(TAG, "post submitted to API." + signInResponse.getMessage());
                        updateUI(signInResponse);
                    }
                });

    }

}
