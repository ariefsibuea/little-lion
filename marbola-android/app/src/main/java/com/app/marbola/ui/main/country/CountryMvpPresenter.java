package com.app.marbola.ui.main.country;

import com.app.marbola.ui.base.MvpPresenter;

/**
 * Created by arief on 07/05/18.
 */

public interface CountryMvpPresenter<V extends CountryMvpView>
        extends MvpPresenter<V> {

    void onViewPrepared();

}
