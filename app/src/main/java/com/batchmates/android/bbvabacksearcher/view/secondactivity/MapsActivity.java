package com.batchmates.android.bbvabacksearcher.view.secondactivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.batchmates.android.bbvabacksearcher.R;
import com.batchmates.android.bbvabacksearcher.injection.secondactivity.DaggerSecondActivityComponent;
import com.batchmates.android.bbvabacksearcher.model.personalpojo.BankPojo;
import com.batchmates.android.bbvabacksearcher.view.thirdactivity.Main2Activity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, SecondActivityContract.View ,LocationListener{

    @Inject
    SecondActivityPresenter presenter;

    List<BankPojo> currentList=new ArrayList<>();
    private GoogleMap mMap;
    private DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setUpDagger();
        presenter.addView(this);
        ButterKnife.bind(this);
        presenter.findLocation(this);


    }

    @BindView(R.id.rvMyRecycler)
    RecyclerView recycler;



    private void setUpDagger() {
        DaggerSecondActivityComponent.create().inject(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(33,-70);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void error() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater m = getMenuInflater();
        m.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.itemList:
                recycler.setVisibility(View.VISIBLE);
                break;

            case R.id.itemMap:
                recycler.setVisibility(View.GONE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void bankLocations(List<BankPojo> bankPojos) {

        currentList=bankPojos;
        for (int i = 0; i < bankPojos.size(); i++) {
            marker= mMap.addMarker(new MarkerOptions()
                    .position(bankPojos.get(i).getLatLng())
                    .title(bankPojos.get(i).getName())
                    .snippet(bankPojos.get(i).getAddress()));
//            mMap.addMarker(new MarkerOptions()
//                    .position(bankPojos.get(i).getLatLng())
//                    .title(bankPojos.get(i).getName())
//                    .snippet(bankPojos.get(i).getAddress()));
            marker.setTag(bankPojos.get(i));
        }
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                BankPojo thisMarker= (BankPojo) marker.getTag();
                Intent intent= new Intent(MapsActivity.this, Main2Activity.class);
                intent.putExtra("BANKNAME",thisMarker.getName());
                intent.putExtra("BANKADDRESS",thisMarker.getAddress());
                intent.putExtra("PICTURE",thisMarker.getIcon());
                intent.putExtra("RATING",thisMarker.getRating());
                startActivity(intent);
            }
        });
        CameraUpdate camera=CameraUpdateFactory.newLatLngZoom(bankPojos.get(bankPojos.size()-1).getLatLng(),15);
        mMap.animateCamera(camera);
        layoutManager= new LinearLayoutManager(this);
        recyclerViewAdapter=new RecyclerViewAdapter(bankPojos);
        recycler.setLayoutManager(layoutManager);
        recycler.setItemAnimator(itemAnimator);
        recycler.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void locationFound(LatLng latLng) {

        presenter.getBanksNeerMe(latLng);
    }

    // when we want to deal with location changes
    @Override
    public void onLocationChanged(Location location) {

        presenter.findLocation(this);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
