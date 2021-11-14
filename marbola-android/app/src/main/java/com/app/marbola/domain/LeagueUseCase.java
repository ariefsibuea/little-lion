package com.app.marbola.domain;

import com.app.marbola.data.DataManager;
import com.app.marbola.data.network.model.LeagueResponse;
import com.app.marbola.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by arief on 11/05/18.
 */

public class LeagueUseCase extends UseCase<LeagueResponse, Void> {

    private final DataManager mDataManager;

    @Inject
    public LeagueUseCase(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
        this.mDataManager = dataManager;
    }

    @Override
    public Observable<LeagueResponse> buildUseCaseObservable(Void aVoid) {
        return mDataManager.getAllLeague();
    }

}
