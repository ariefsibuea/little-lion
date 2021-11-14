package com.app.marbola.ui.main.schedule;

import com.app.marbola.data.DataManager;
import com.app.marbola.data.network.model.ScheduleResponse;
import com.app.marbola.domain.ScheduleUseCase;
import com.app.marbola.ui.base.BasePresenter;
import com.app.marbola.utils.AppLogger;
import com.app.marbola.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by arief on 11/05/18.
 */

public class SchedulePresenter<V extends ScheduleMvpView> extends BasePresenter<V>
        implements ScheduleMvpPresenter<V> {

    private static final String TAG = "OnCountry";

    private final ScheduleUseCase mScheduleUseCase;

    @Inject
    public SchedulePresenter(ScheduleUseCase scheduleUseCase,
                             DataManager dataManager,
                             SchedulerProvider schedulerProvider,
                             CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
        this.mScheduleUseCase = scheduleUseCase;
    }

    @Override
    public void onViewPrepared() {
        getMvpView().showLoading();
        getCompositeDisposable().add(mScheduleUseCase
                .execute(null)
                .subscribe(new Consumer<ScheduleResponse>() {
                    @Override
                    public void accept(ScheduleResponse scheduleResponse) throws Exception {
                        if (scheduleResponse != null && scheduleResponse.getData() != null) {
                            getMvpView().updateSchedule(scheduleResponse.getData());
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

                        AppLogger.d(TAG, throwable.getMessage());
                        throwable.printStackTrace();
                    }
                }));
    }

}
