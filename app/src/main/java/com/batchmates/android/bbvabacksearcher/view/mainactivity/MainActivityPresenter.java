package com.batchmates.android.bbvabacksearcher.view.mainactivity;

import android.content.Context;
import android.content.Intent;

import com.batchmates.android.bbvabacksearcher.view.secondactivity.MapsActivity;

/**
 * Created by Android on 7/29/2017.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter{

    MainActivityContract.View view;

    @Override
    public void addView(MainActivityContract.View view) {
        this.view=view;
    }

    @Override
    public void removeView() {
        this.view=null;
    }

    @Override
    public void StartMapACtivity(Context context) {
        Intent intent= new Intent(context, MapsActivity.class);
        context.startActivity(intent);
    }
}
