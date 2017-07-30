package com.batchmates.android.bbvabacksearcher.view.secondactivity;

import android.content.Context;

import com.batchmates.android.bbvabacksearcher.BasePresenter;
import com.batchmates.android.bbvabacksearcher.BaseView;
import com.batchmates.android.bbvabacksearcher.model.personalpojo.BankPojo;
import com.batchmates.android.bbvabacksearcher.view.mainactivity.MainActivityContract;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Android on 7/29/2017.
 */

public interface SecondActivityContract {

    interface View extends BaseView
    {
        void bankLocations(List<BankPojo> bankPojos);
        void locationFound(LatLng latLng);
    }


    interface Presenter extends BasePresenter<View>
    {
        void getBanksNeerMe(LatLng location);
        void findLocation(Context context);
    }
}
