package com.app.marbola.di.component;

import com.app.marbola.di.PerActivity;
import com.app.marbola.di.module.ActivityModule;
import com.app.marbola.ui.main.MainActivity;
import com.app.marbola.ui.main.country.CountryFragment;
import com.app.marbola.ui.main.league.LeagueFragment;
import com.app.marbola.ui.main.schedule.ScheduleFragment;
import com.app.marbola.ui.splash.SplashActivity;

import dagger.Component;

/**
 * Created by arief on 03/05/18.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(SplashActivity activity);

    void inject(CountryFragment fragment);

    void inject(ScheduleFragment fragment);

    void inject(LeagueFragment fragment);

}
