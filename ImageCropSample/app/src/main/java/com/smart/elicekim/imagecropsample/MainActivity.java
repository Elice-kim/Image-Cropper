package com.smart.elicekim.imagecropsample;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_SELECT_PICTURE = 1001;
    private File mFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button camera = findViewById(R.id.clickcamera);
        Button gallery = findViewById(R.id.clickgallery);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RxPermissions(MainActivity.this)
                        .requestEach(Manifest.permission.CAMERA)
                        .filter(new Predicate<Permission>() {
                            @Override
                            public boolean test(@io.reactivex.annotations.NonNull Permission permission) throws Exception {
                                return permission.granted;
                            }
                        })
                        .subscribe(new Consumer<Permission>() {
                            @Override
                            public void accept(@io.reactivex.annotations.NonNull Permission p) throws Exception {
                                if (p.granted) {
                                    startCamera();
                                }
                            }
                        });
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RxPermissions(MainActivity.this)
                        .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .filter(new Predicate<Boolean>() {
                            @Override
                            public boolean test(@io.reactivex.annotations.NonNull Boolean granted) throws Exception {
                                return granted;
                            }
                        })
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(@io.reactivex.annotations.NonNull Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    pickGallery();
                                }
                            }
                        });
            }
        });
    }

    private void startCamera() {

    }

    private void pickGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        mFile = newFile();
        startActivityForResult(Intent.createChooser(intent, getString(R.string.label_select_picture)), REQUEST_SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_PICTURE) {
            if (resultCode == RESULT_OK) {
                Uri resultUri = data.getData();
                startCropActivity(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
            }
        }else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            handleCropResult(data);
        }
    }

    private void handleCropResult(Intent data) {
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (result != null) {
            ResultActivity.startWithUri(MainActivity.this, result.getUri());
        } else {
            Toast.makeText(MainActivity.this, R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show();
        }
    }

    private void startCropActivity(Uri data) {
        CropImage.activity(data)
                .start(this);
    }

    private File newFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "IMG_" + timeStamp + ".jpg";
        String bucketName = "vinglePic";

        return ImageUtils.getOutputMediaFile(bucketName, imageName);
    }
}
