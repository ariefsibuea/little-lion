package com.app.marbola.domain;

import com.app.marbola.data.DataManager;
import com.app.marbola.data.network.model.CountryResponse;
import com.app.marbola.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by arief on 07/05/18.
 */

public class CountryUseCase extends UseCase<CountryResponse, Void> {

    private final DataManager mDataManager;

    @Inject
    public CountryUseCase(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
        this.mDataManager = dataManager;
    }

    @Override
    public Observable<CountryResponse> buildUseCaseObservable(Void aVoid) {
        return mDataManager.getAllCountry();
    }

}
