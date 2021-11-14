package com.app.marbola.domain;

import com.app.marbola.data.DataManager;
import com.app.marbola.data.network.model.ScheduleResponse;
import com.app.marbola.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by arief on 11/05/18.
 */

public class ScheduleUseCase extends UseCase<ScheduleResponse, Void> {

    private final DataManager mDataManager;

    @Inject
    public ScheduleUseCase(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
        this.mDataManager = dataManager;
    }

    @Override
    public Observable<ScheduleResponse> buildUseCaseObservable(Void aVoid) {
        return mDataManager.getAllSchedule();
    }

}
