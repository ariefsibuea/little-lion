package com.app.marbola.ui.splash;

import com.app.marbola.ui.base.MvpView;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by arief on 09/05/18.
 */

public interface SplashMvpView extends MvpView {

    void openMainActivity();

    void onRunSplashScreen(CompositeDisposable compositeDisposable);

    void startSyncService();

}
