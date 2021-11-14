package com.app.marbola.di.component;

import com.app.marbola.di.PerService;
import com.app.marbola.di.module.ServiceModule;
import com.app.marbola.service.SyncService;

import dagger.Component;

/**
 * Created by arief on 03/05/18.
 */

@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {

    void inject(SyncService service);

}
