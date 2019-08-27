package com.git.ravaee.tezpishir.utility;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.git.ravaee.tezpishir.root.App;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import static android.app.Activity.RESULT_OK;

public class ImageEdit {

    protected App app;
    private static final String TAG = "IMAGE_EDIT";
    private AppCompatActivity activity;


    public ImageEdit(App app, AppCompatActivity activity) {
        this.app = app;
        this.activity = activity;
    }

    public void pickImage() {


        CropImage.startPickImageActivity(this.activity);

    }

    private void cropImage(Uri imageUri){
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                //REQUEST COMPRESS SIZE
                .setRequestedSize(800, 800)
                .start(this.activity);
    }

    public Uri checkActivityResultForCropImage(int requestCode, int resultCode, Intent data, ImageView profile_image, Uri photoUri){
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this.activity, data);
            cropImage(imageUri);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                profile_image.setImageURI(result.getUri());
                photoUri = result.getUri();
            }
        }
        return photoUri;
    }
}
