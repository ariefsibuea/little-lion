package com.app.marbola.ui.main.league;

import com.app.marbola.ui.base.MvpPresenter;

/**
 * Created by arief on 11/05/18.
 */

public interface LeagueMvpPresenter<V extends LeagueMvpView>
        extends MvpPresenter<V> {

    void onViewPrepared();

}
