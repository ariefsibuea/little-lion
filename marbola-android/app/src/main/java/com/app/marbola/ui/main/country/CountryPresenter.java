package com.app.marbola.ui.main.country;

import com.app.marbola.data.DataManager;
import com.app.marbola.data.network.model.CountryResponse;
import com.app.marbola.domain.CountryUseCase;
import com.app.marbola.ui.base.BasePresenter;
import com.app.marbola.utils.AppLogger;
import com.app.marbola.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by arief on 07/05/18.
 */

public class CountryPresenter<V extends CountryMvpView> extends BasePresenter<V>
        implements CountryMvpPresenter<V> {

    private static final String TAG = "OnCountry";

    private final CountryUseCase mCountryUseCase;

    @Inject
    public CountryPresenter(CountryUseCase countryUseCase,
                            SchedulerProvider schedulerProvider,
                            DataManager dataManager,
                            CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
        this.mCountryUseCase = countryUseCase;
    }

    @Override
    public void onViewPrepared() {
        getMvpView().showLoading();
        getCompositeDisposable().add(mCountryUseCase
                .execute(null)
                .subscribe(new Consumer<CountryResponse>() {
                    @Override
                    public void accept(CountryResponse countryResponse) throws Exception {
                        if (countryResponse != null && countryResponse.getData() != null) {
                            getMvpView().updateCountry(countryResponse.getData());
                        }
                        getMvpView().hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoading();

                        // handle the error here
                        // handleApiError(throwable.getMessage());
                        AppLogger.d(TAG, throwable.getMessage());
                        throwable.printStackTrace();
                    }
                }));
    }

}
