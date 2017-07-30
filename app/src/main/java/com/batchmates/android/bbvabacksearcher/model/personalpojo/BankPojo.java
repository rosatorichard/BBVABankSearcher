package com.batchmates.android.bbvabacksearcher.model.personalpojo;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Android on 7/30/2017.
 */

public class BankPojo {

    String name;
    String address;
    LatLng latLng;
    double rating;
    String icon;

    public BankPojo(String name, String address, LatLng latLng, double rating, String icon) {
        this.name = name;
        this.address = address;
        this.latLng=latLng;
        this.rating = rating;
        this.icon = icon;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getRating() {
        return rating;
    }

    public String getIcon() {
        return icon;
    }
}
