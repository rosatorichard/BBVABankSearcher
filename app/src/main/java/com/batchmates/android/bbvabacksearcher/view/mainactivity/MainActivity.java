package com.batchmates.android.bbvabacksearcher.view.mainactivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.batchmates.android.bbvabacksearcher.R;
import com.batchmates.android.bbvabacksearcher.injection.mainactivity.DaggerMainActivityComponent;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View{

    private static final String TAG = "MainActivity";
    private static final int MY_PERMISSIONS_REQUEST_REQUEST_LOCATION = 0;
    @Inject MainActivityPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpDagger();
        presenter.addView(this);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onCreate: Permission not granted");

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.d(TAG, "onCreate: We need this");

                Toast.makeText(this,"App can not Continue Location Tracking is off",Toast.LENGTH_LONG).show();
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_REQUEST_LOCATION);
                Log.d(TAG, "onCreate: Requesting permission");


                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
                return;
            }
        }
        else
        {
            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        startSecondActivity();
                        Log.d(TAG, "run: Made it");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_REQUEST_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"Thank You For Permission",Toast.LENGTH_SHORT).show();
                    Thread thread=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2000);
                                startSecondActivity();
                                Log.d(TAG, "run: Made it");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    Toast.makeText(this,"Can not Continue without Permission",Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
        }
    }

    private void startSecondActivity() {

        presenter.StartMapACtivity(this);
        finish();
    }

    private void setUpDagger() {
        DaggerMainActivityComponent.create().inject(this);
    }

    @Override
    public void error() {

    }
}
