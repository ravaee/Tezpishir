package com.git.ravaee.tezpishir.activity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import com.git.ravaee.tezpishir.R;
import com.git.ravaee.tezpishir.model.User;
import com.git.ravaee.tezpishir.root.App;
import com.git.ravaee.tezpishir.utility.ImageEdit;
import com.git.ravaee.tezpishir.viewModel.user.UserViewModel;
import com.git.ravaee.tezpishir.viewModel.user.UserViewModelFactory;
import com.squareup.picasso.Picasso;


public class UserEditActivity extends AppCompatActivity {


    protected static final String TAG = "EDIT_ACTIVITY";
    protected App app = null;
    private Uri photoURI = null;
    private User user = null;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    private ImageEdit imageEdit;

    @BindView(R.id.user_name_text)
    EditText name_text;

    @BindView(R.id.phone_number_text)
    EditText phone_number_text;

    @BindView(R.id.experience_text)
    EditText experience_text;

    @BindView(R.id.area_of_service_text)
    EditText area_service_text;

    @BindView(R.id.profile_image)
    ImageView profile_image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);
        app = (App)this.getApplication();
        ButterKnife.bind(this);
        imageEdit = new ImageEdit(app,UserEditActivity.this);
        getUser();
    }

    private void updateUI(){

        name_text.setText(user.getNickName());
        phone_number_text.setText(user.getPhoneNumber());
        experience_text.setText(user.getExperience());
        area_service_text.setText(user.getAreaOfService());

        if(this.user.getUploadImage() != null){
            Picasso.with(app.getApplicationContext()).load(this.user.getUploadImage())
                    .into(profile_image);
        }else{
            Picasso.with(app.getApplicationContext()).load(this.user.getImage())
                    .into(profile_image);
        }
    }

    @OnClick(R.id.add_picture)
    protected void choosePicture(){
        requestPermissionsAndPickImage();

    }

    @OnClick(R.id.updateButton)
    protected void updateUser(){

        UserViewModel userViewModel = ViewModelProviders.of(this,new UserViewModelFactory(this.app)).get(UserViewModel.class);
        userViewModel.updateUser(this.user.getId(),phone_number_text.getText().toString(),area_service_text.getText().toString(),
                experience_text.getText().toString(),name_text.getText().toString(),photoURI).observe(this, userResponse -> this.getUser());

    }


    private void getUser(){

        UserViewModel userViewModel = ViewModelProviders.of(this,new UserViewModelFactory(this.app)).get(UserViewModel.class);
        userViewModel.getUsers().observe(this, userResponse -> {
            this.user = userResponse.getUser();
            updateUI();
        });

    }

    protected void requestPermissionsAndPickImage(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_WRITE_PERMISSION);

        } else {

            imageEdit.pickImage();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            imageEdit.pickImage();
        }else{
            requestPermissionsAndPickImage();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        photoURI = imageEdit.checkActivityResultForCropImage(requestCode,resultCode,data,this.profile_image,photoURI);
    }







}
