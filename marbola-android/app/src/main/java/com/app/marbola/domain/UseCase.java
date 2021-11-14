package com.app.marbola.domain;

import com.app.marbola.utils.rx.SchedulerProvider;

import io.reactivex.Observable;

/**
 * Created by arief on 07/05/18.
 */

public abstract class UseCase<T, Params> {

    private final SchedulerProvider mSchedulerProvider;

    public UseCase(SchedulerProvider schedulerProvider) {
        this.mSchedulerProvider = schedulerProvider;
    }

    public abstract Observable<T> buildUseCaseObservable(Params params);

    public Observable<T> execute(Params params) {
        return buildUseCaseObservable(params)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui());
    }

}
