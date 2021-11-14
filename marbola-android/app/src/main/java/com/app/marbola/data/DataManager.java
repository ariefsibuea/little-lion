package com.app.marbola.data;

import com.app.marbola.data.network.model.CountryResponse;
import com.app.marbola.data.network.model.LeagueResponse;
import com.app.marbola.data.network.model.ScheduleResponse;

import io.reactivex.Observable;

/**
 * Created by arief on 04/05/18.
 */

public interface DataManager {

    Observable<CountryResponse> getAllCountry();

    Observable<ScheduleResponse> getAllSchedule();

    Observable<LeagueResponse> getAllLeague();

}
