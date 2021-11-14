package com.app.marbola.ui.main;

import com.app.marbola.data.DataManager;
import com.app.marbola.ui.base.BasePresenter;
import com.app.marbola.ui.base.MvpView;
import com.app.marbola.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by arief on 07/05/18.
 */

public class MainPresenter<V extends MvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V> {

    private static final String TAG = "MainPresenter";

    @Inject
    public MainPresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onDrawerOptionLiveClick() {

    }

    @Override
    public void onDrawerOptionFinalTimeClick() {

    }

    @Override
    public void onDrawerOptionScheduleClick() {

    }

    @Override
    public void onDrawerOptionLeagueClick() {

    }

    @Override
    public void onNavMenuCreated() {
        if (!isViewAttached()) {
            return;
        }
    }
}
