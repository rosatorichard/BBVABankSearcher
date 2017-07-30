package com.batchmates.android.bbvabacksearcher.injection.secondactivity;

import com.batchmates.android.bbvabacksearcher.view.secondactivity.SecondActivityPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Android on 7/29/2017.
 */
@Module
public class SecondActivityModule {
    @Provides
    public SecondActivityPresenter secondActivityPresenter()
    {
        return new SecondActivityPresenter();
    }
}
