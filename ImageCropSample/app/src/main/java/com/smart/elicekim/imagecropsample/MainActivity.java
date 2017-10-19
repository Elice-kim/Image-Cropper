package com.smart.elicekim.imagecropsample;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;


public class MainActivity extends AppCompatActivity {

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

    }
}
