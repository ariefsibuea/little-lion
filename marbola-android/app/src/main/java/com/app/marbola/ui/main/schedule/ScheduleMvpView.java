package com.app.marbola.ui.main.schedule;

import com.app.marbola.data.network.model.ScheduleResponse;
import com.app.marbola.ui.base.MvpView;

import java.util.List;

/**
 * Created by arief on 11/05/18.
 */

public interface ScheduleMvpView extends MvpView {

    void updateSchedule(List<ScheduleResponse.Schedule> scheduleList);

}
