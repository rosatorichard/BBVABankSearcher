package com.batchmates.android.bbvabacksearcher.view.secondactivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.batchmates.android.bbvabacksearcher.model.BBVABankPlaces;
import com.batchmates.android.bbvabacksearcher.model.personalpojo.BankPojo;
import com.batchmates.android.bbvabacksearcher.view.mainactivity.RetroFitHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Android on 7/29/2017.
 */

public class SecondActivityPresenter implements SecondActivityContract.Presenter {

    List<BankPojo> banksInArea = new ArrayList<>();
    private LatLng latlng;
    private double rating;
    private FusedLocationProviderClient fusedClient;
    private static final String TAG = "SecondActivityPresenter";
    SecondActivityContract.View view;

    @Override
    public void addView(SecondActivityContract.View view) {
        this.view = view;

    }

    @Override
    public void removeView() {
        this.view = null;
    }

    @Override
    public void getBanksNeerMe(LatLng location) {
        retrofit2.Call<BBVABankPlaces> call = new RetroFitHelper().getLocations(location);
        call.enqueue(new Callback<BBVABankPlaces>() {
            @Override
            public void onResponse(Call<BBVABankPlaces> call, Response<BBVABankPlaces> response) {
                for (int i = 0; i < response.body().getResults().size(); i++) {

                    if (response.body().getResults().get(i).getRating() == null) {
                        rating = 0;
                    } else {
                        rating = response.body().getResults().get(i).getRating();
                    }
                    if(response.body().getResults().get(i).getPhotos()==null)
                    {
                        Log.d(TAG, "onResponse: No Photo");
                    }
                    else {
                        Log.d(TAG, "onResponse: " + response.body().getResults().get(i).getPhotos().get(0).getHtmlAttributions().get(0));
                    }
                    latlng = new LatLng(response.body().getResults().get(i).getGeometry().getLocation().getLat(),
                            response.body().getResults().get(i).getGeometry().getLocation().getLng());
                    banksInArea.add(new BankPojo(response.body().getResults().get(i).getName(),
                            response.body().getResults().get(i).getFormattedAddress(),
                            latlng,
                            rating,
                            response.body().getResults().get(i).getIcon()));
                }
                view.bankLocations(banksInArea);
            }

            @Override
            public void onFailure(Call<BBVABankPlaces> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }

    @Override
    public void findLocation(Context contex) {
        fusedClient = LocationServices.getFusedLocationProviderClient(contex);

        if (ActivityCompat.checkSelfPermission(contex, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(contex, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                latlng=new LatLng(location.getLatitude(),location.getLongitude());
                view.locationFound(latlng);

            }
        });
    }
}
