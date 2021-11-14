package com.app.marbola.data;

import android.content.Context;

import com.app.marbola.data.network.ApiHelper;
import com.app.marbola.data.network.model.CountryResponse;
import com.app.marbola.data.network.model.LeagueResponse;
import com.app.marbola.data.network.model.ScheduleResponse;
import com.app.marbola.di.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by arief on 04/05/18.
 */

@Singleton
public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";

    private final Context mContext;
    private final ApiHelper mApiHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          Retrofit retrofit) {
        mContext = context;
        mApiHelper = retrofit.create(ApiHelper.class);
    }

    @Override
    public Observable<CountryResponse> getAllCountry() {
        return mApiHelper.getAllCountryData();
    }

    @Override
    public Observable<ScheduleResponse> getAllSchedule() {
        return mApiHelper.getAllScheduleData();
    }

    @Override
    public Observable<LeagueResponse> getAllLeague() {
        return mApiHelper.getAllLeagueData();
    }

}
