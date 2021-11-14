package com.app.marbola.data.network;

import com.app.marbola.data.network.model.CountryResponse;
import com.app.marbola.data.network.model.LeagueResponse;
import com.app.marbola.data.network.model.ScheduleResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by arief on 04/05/18.
 */

public interface ApiHelper {

    @GET("/country")
    Observable<CountryResponse> getAllCountryData();

    @GET("/schedule")
    Observable<ScheduleResponse> getAllScheduleData();

    @GET("/league")
    Observable<LeagueResponse> getAllLeagueData();

}
