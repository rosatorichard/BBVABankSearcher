package com.batchmates.android.bbvabacksearcher.injection.mainactivity;

import com.batchmates.android.bbvabacksearcher.view.mainactivity.MainActivity;

import dagger.Component;

/**
 * Created by Android on 7/29/2017.
 */
@Component(modules = MainActivityModule.class)
public interface MainActivityComponent {

    void inject(MainActivity mainActivity);
}
