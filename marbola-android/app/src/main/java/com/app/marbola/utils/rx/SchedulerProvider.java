package com.app.marbola.utils.rx;

import io.reactivex.Scheduler;

/**
 * Created by arief on 07/05/18.
 */

public interface SchedulerProvider {

    Scheduler ui();

    Scheduler computation();

    Scheduler io();

}
