package com.app.marbola.di.module;

import android.app.Service;

import dagger.Module;

/**
 * Created by arief on 03/05/18.
 */

@Module
public class ServiceModule {

    private final Service mService;

    public ServiceModule(Service service) {
        mService = service;
    }

}
