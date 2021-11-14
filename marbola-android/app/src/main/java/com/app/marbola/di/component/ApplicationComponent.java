package com.app.marbola.di.component;

import android.app.Application;
import android.content.Context;

import com.app.marbola.MvpApp;
import com.app.marbola.data.DataManager;
import com.app.marbola.di.ApplicationContext;
import com.app.marbola.di.module.ApplicationModule;
import com.app.marbola.service.SyncService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by arief on 03/05/18.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MvpApp mvpApp);

    void inject(SyncService service);

    @ApplicationContext
    Context context();

    Application getApplication();

    DataManager getDataManager();

}
