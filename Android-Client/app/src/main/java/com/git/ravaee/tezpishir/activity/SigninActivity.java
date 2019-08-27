package com.git.ravaee.tezpishir.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.git.ravaee.tezpishir.R;
import com.git.ravaee.tezpishir.model.response.SignInResponse;
import com.git.ravaee.tezpishir.root.App;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SigninActivity extends AppCompatActivity {

    private static final String TAG = "SIGNIN_ACTIVITY";
    GoogleSignInClient mGoogleSignInClient;
    App app = null;

    @BindView(R.id.sign_in_button) SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        app = (App)this.getApplication();
        ButterKnife.bind(this);

        signInButton.setColorScheme(SignInButton.COLOR_DARK);

        setGooglePlusButtonText(signInButton,"Sign in with Google");
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 9001) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void updateUI(SignInResponse response) {

        if(response != null){

            app.getSession().saveEmail(response.getUser().getEmail());
            app.getSession().saveImage(response.getUser().getImage());
            app.getSession().saveName(response.getUser().getName());
            app.getSession().saveToken(response.getUser().getToken());

            Intent intent = new Intent(this, GroupActivity.class);
            startActivity(intent);
        }

    }
    @OnClick(R.id.sign_in_button)
    protected void signIn() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 9001);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {

            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            if(account != null){

                signInAndGetJWToken(account);
            }

        } catch (ApiException e) {

            updateUI(null);
            Log.e(TAG, "handleSignInResult: " + e.toString());
        }
    }

    void signInAndGetJWToken(final GoogleSignInAccount account){

        app.getAuthService().getUser(account.getIdToken(), account.getEmail())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SignInResponse>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Unable to submit post to API." + e);
                        updateUI(null);
                    }

                    @Override
                    public void onNext(SignInResponse signInResponse) {
                        Log.i(TAG, "post submitted to API." + signInResponse.getUser().getToken());
                        updateUI(signInResponse);
                    }
                });
    }

    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {

        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
        }
    }

}
