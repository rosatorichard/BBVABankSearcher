package com.batchmates.android.bbvabacksearcher.injection.mainactivity;

import com.batchmates.android.bbvabacksearcher.view.mainactivity.MainActivityPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Android on 7/29/2017.
 */
@Module
public class MainActivityModule {

    @Provides
    public MainActivityPresenter mainActivityPresenter()
    {
        return new MainActivityPresenter();
    }
}
