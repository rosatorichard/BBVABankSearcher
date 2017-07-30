package com.batchmates.android.bbvabacksearcher.injection.secondactivity;

import com.batchmates.android.bbvabacksearcher.view.secondactivity.MapsActivity;

import dagger.Component;

/**
 * Created by Android on 7/29/2017.
 */
@Component(modules = SecondActivityModule.class)
public interface SecondActivityComponent {
    void inject(MapsActivity mapsActivity);
}
