package com.app.marbola.ui.main;

import com.app.marbola.ui.base.MvpPresenter;
import com.app.marbola.ui.base.MvpView;

/**
 * Created by arief on 07/05/18.
 */

public interface MainMvpPresenter<V extends MvpView> extends MvpPresenter<V> {

    void onDrawerOptionLiveClick();

    void onDrawerOptionFinalTimeClick();

    void onDrawerOptionScheduleClick();

    void onDrawerOptionLeagueClick();

    void onNavMenuCreated();

}
