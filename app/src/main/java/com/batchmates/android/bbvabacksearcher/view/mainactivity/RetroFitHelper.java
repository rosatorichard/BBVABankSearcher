package com.batchmates.android.bbvabacksearcher.view.mainactivity;

import com.batchmates.android.bbvabacksearcher.model.BBVABankPlaces;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Android on 7/30/2017.
 */

public class RetroFitHelper {

    private static final String BASE_URL = "https://maps.googleapis.com/";
    private static final String BANK="BBVA+Compass";
    private static final String API_KEY="AIzaSyDWsEhJ1LOV4O-vOh_3xsMfjKntiMcX-fM";
    private String location="30,-87";
    private static final int radius=10000;

    //https://maps.googleapis.com/maps/api/place/textsearch/json?query=BBVA+Compass&location=30,-87&radius=10000&key=AIzaSyDWsEhJ1LOV4O-vOh_3xsMfjKntiMcX-fM
    public static Retrofit Create()
    {
        Retrofit retro=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retro;
    }


    public Call<BBVABankPlaces> getLocations(LatLng newLocation)
    {
        String theLocation=newLocation.latitude+","+newLocation.longitude;
        Retrofit retro=Create();
        BBVALocations bbvaLocation=retro.create(RetroFitHelper.BBVALocations.class);
        return bbvaLocation.getLocation(BANK,theLocation,radius,API_KEY);
    }



    public interface BBVALocations
    {
        @GET("maps/api/place/textsearch/json")
        Call<BBVABankPlaces> getLocation(@Query("query")String bank,@Query("location")String location,@Query("radius")int radius,@Query("key")String key);
    }

}
