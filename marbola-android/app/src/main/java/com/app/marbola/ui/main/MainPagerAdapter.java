package com.app.marbola.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.marbola.ui.main.country.CountryFragment;
import com.app.marbola.ui.main.league.LeagueFragment;
import com.app.marbola.ui.main.schedule.ScheduleFragment;

/**
 * Created by arief on 07/05/18.
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private int mTabCount;

    public MainPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mTabCount = 0;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            // case
            case 0:
                return CountryFragment.newInstance();
            case 1:
                return CountryFragment.newInstance();
            case 2:
                return ScheduleFragment.newInstance();
            case 3:
                return LeagueFragment.newInstance();
            // default
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTabCount;
    }

    public void setCount(int count) {
        mTabCount = count;
    }

}
