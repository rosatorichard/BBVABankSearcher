package com.batchmates.android.bbvabacksearcher.view.mainactivity;

import android.content.Context;
import android.view.View;

import com.batchmates.android.bbvabacksearcher.BasePresenter;
import com.batchmates.android.bbvabacksearcher.BaseView;

/**
 * Created by Android on 7/29/2017.
 */

public interface MainActivityContract {

    interface View extends BaseView
    {

    }

    interface Presenter extends BasePresenter<View>
    {
        void StartMapACtivity(Context context);
    }
}
