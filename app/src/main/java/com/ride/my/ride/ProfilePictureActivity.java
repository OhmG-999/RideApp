package com.ride.my.ride;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePictureActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private CircleImageView pictureImageView;
    private ProgressBar imgProgress;
    private Button savePictureBtn;
    private Button skipPicture;
    private final String TAG = "RideApp -->";
    protected Uri imageUri = null;
    private String user_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture);

        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();

        pictureImageView = findViewById(R.id.user_profile_img);
        savePictureBtn = findViewById(R.id.save_img_btn);
        skipPicture = findViewById(R.id.skip_btn);
        imgProgress = findViewById(R.id.img_upload_progress);

        pictureImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check if the version is equal or greater than Marshmallow
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    // verify is the app has already read permissions to the internal or/and external storage
                    if(ContextCompat.checkSelfPermission(
                            ProfilePictureActivity.this,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                        try{

                            // display a toast to let the user know that permission is denied, log an entry and prompt the user if access can be granted
                            Toast.makeText(ProfilePictureActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                            Log.i(TAG, "Permission need to be granted to access the user external storage and/or media library");
                            ActivityCompat.requestPermissions(
                                    ProfilePictureActivity.this,
                                    new String [] {android.Manifest.permission.READ_EXTERNAL_STORAGE},
                                    1);
                        }
                        catch (Exception e){

                            Log.e(TAG, "Permission could not be granted to access the user external storage and/or media library");
                        }
                    }
                    else{

                        try{
                            Log.i(TAG, "Permission granted to access the user external storage and/or media library");
                            openGallery();
                        }
                        catch (Exception e){

                            Log.e(TAG, "Cannot open phone media gallery");
                        }
                    }
                }
                else{
                    // if the version is lower than Marshmallow, permission does not need requested
                    openGallery();
                }
            }
        });

        savePictureBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(imageUri != null){

                    try{
                        setUser_ID(mAuth.getCurrentUser().getUid());
                        imgProgress.setVisibility(View.VISIBLE);

                        StorageReference imagePath = mStorage.child("profile_images").child(getUser_ID() + ".jpg");

                        imagePath.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                if(task.isSuccessful()){

                                    Toast.makeText(ProfilePictureActivity.this, "Your profile picture has been successfully saved", Toast.LENGTH_LONG).show();
                                    Uri download_url = task.getResult().getUploadSessionUri();
                                    String picture_url = download_url.toString();
                                    Log.i(TAG,getUser_ID());

                                    mDatabase = FirebaseDatabase.getInstance().getReference("Users");
                                    mDatabase.child(getUser_ID()).child("Picture").setValue(picture_url);

                                }
                                else{

                                    String error = task.getException().getMessage();
                                    Toast.makeText(ProfilePictureActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
                                }

                                imgProgress.setVisibility(View.INVISIBLE);
                            }
                        });

                        sendToMain();

                    }catch (Exception e){

                        Log.e(TAG,"=--->",e);
                    }
                }
            }
        });

        skipPicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                sendToMain();
            }
        });
    }

    private void openGallery(){

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(ProfilePictureActivity.this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                imageUri = result.getUri();
                pictureImageView.setImageURI(imageUri);

            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
                Log.e(TAG, "Image could not been cropped", error);
            }
        }
    }

    private void sendToMain() {

        Intent mainIntent = new Intent(ProfilePictureActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    public String getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }
}
