package com.batchmates.android.bbvabacksearcher;

/**
 * Created by Android on 7/29/2017.
 */

public interface BasePresenter <V extends BaseView>{

    void addView(V view);

    void removeView();
}
