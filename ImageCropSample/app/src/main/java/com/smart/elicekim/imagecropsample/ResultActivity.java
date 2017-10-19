package com.smart.elicekim.imagecropsample;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

public class ResultActivity extends AppCompatActivity {

    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;

    public static void startWithUri(@NonNull Context context, @NonNull Uri uri) {
        Intent intent = new Intent(context, ResultActivity.class);
        intent.setData(uri);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ImageView cropImageView = findViewById(R.id.cropImageView);
        cropImageView.setImageURI(getIntent().getData());

    }

}
