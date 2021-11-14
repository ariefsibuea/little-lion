package com.app.marbola.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.app.marbola.data.network.model.CountryResponse;
import com.app.marbola.data.network.model.LeagueResponse;
import com.app.marbola.data.network.model.ScheduleResponse;
import com.app.marbola.di.ActivityContext;
import com.app.marbola.di.PerActivity;
import com.app.marbola.ui.main.MainMvpPresenter;
import com.app.marbola.ui.main.MainMvpView;
import com.app.marbola.ui.main.MainPagerAdapter;
import com.app.marbola.ui.main.MainPresenter;
import com.app.marbola.ui.main.country.CountryAdapter;
import com.app.marbola.ui.main.country.CountryMvpPresenter;
import com.app.marbola.ui.main.country.CountryMvpView;
import com.app.marbola.ui.main.country.CountryPresenter;
import com.app.marbola.ui.main.league.LeagueAdapter;
import com.app.marbola.ui.main.league.LeagueMvpPresenter;
import com.app.marbola.ui.main.league.LeagueMvpView;
import com.app.marbola.ui.main.league.LeaguePresenter;
import com.app.marbola.ui.main.schedule.ScheduleAdapter;
import com.app.marbola.ui.main.schedule.ScheduleMvpPresenter;
import com.app.marbola.ui.main.schedule.ScheduleMvpView;
import com.app.marbola.ui.main.schedule.SchedulePresenter;
import com.app.marbola.ui.splash.SplashMvpPresenter;
import com.app.marbola.ui.splash.SplashMvpView;
import com.app.marbola.ui.splash.SplashPresenter;
import com.app.marbola.utils.rx.AppSchedulerProvider;
import com.app.marbola.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by arief on 03/05/18.
 */

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    CountryMvpPresenter<CountryMvpView> provideCountryMvpPresenter(CountryPresenter<CountryMvpView> presenter) {
        return presenter;
    }

    @Provides
    ScheduleMvpPresenter<ScheduleMvpView> provideScheduleMvpPresenter(SchedulePresenter<ScheduleMvpView> presenter) {
        return presenter;
    }

    @Provides
    LeagueMvpPresenter<LeagueMvpView> provideLeagueMvpPresenter(LeaguePresenter<LeagueMvpView> presenter) {
        return presenter;
    }

    @Provides
    MainPagerAdapter provideMainPagerAdapter(AppCompatActivity activity) {
        return new MainPagerAdapter(activity.getSupportFragmentManager());
    }

    @Provides
    CountryAdapter provideCountryAdapter() {
        return new CountryAdapter(new ArrayList<CountryResponse.Country>());
    }

    @Provides
    ScheduleAdapter provideScheduleAdapter() {
        return new ScheduleAdapter(new ArrayList<ScheduleResponse.Schedule>());
    }

    @Provides
    LeagueAdapter provideLeagueAdapter() {
        return new LeagueAdapter(new ArrayList<LeagueResponse.League>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }

}
