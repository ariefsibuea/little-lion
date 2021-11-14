package com.app.marbola.ui.main.league;

import com.app.marbola.data.DataManager;
import com.app.marbola.data.network.model.LeagueResponse;
import com.app.marbola.domain.LeagueUseCase;
import com.app.marbola.ui.base.BasePresenter;
import com.app.marbola.utils.AppLogger;
import com.app.marbola.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by arief on 11/05/18.
 */

public class LeaguePresenter<V extends LeagueMvpView> extends BasePresenter<V>
        implements LeagueMvpPresenter<V> {

    private static final String TAG = "OnLeague";

    private final LeagueUseCase mLeagueUseCase;

    @Inject
    public LeaguePresenter(LeagueUseCase leagueUseCase,
                           DataManager dataManager,
                           SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
        this.mLeagueUseCase = leagueUseCase;
    }

    @Override
    public void onViewPrepared() {
        getMvpView().showLoading();
        getCompositeDisposable().add(mLeagueUseCase
                .execute(null)
                .subscribe(new Consumer<LeagueResponse>() {
                    @Override
                    public void accept(LeagueResponse leagueResponse) throws Exception {
                        if (leagueResponse != null && leagueResponse.getData() != null) {
                            getMvpView().updateLeague(leagueResponse.getData());
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
