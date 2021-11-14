package com.app.marbola.ui.main.schedule;

import com.app.marbola.ui.base.MvpPresenter;

/**
 * Created by arief on 11/05/18.
 */

public interface ScheduleMvpPresenter<V extends ScheduleMvpView>
        extends MvpPresenter<V> {

    void onViewPrepared();

}
