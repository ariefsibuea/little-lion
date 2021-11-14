package com.app.marbola.ui.main.league;

import com.app.marbola.data.network.model.LeagueResponse;
import com.app.marbola.ui.base.MvpView;

import java.util.List;

/**
 * Created by arief on 11/05/18.
 */

public interface LeagueMvpView extends MvpView {

    void updateLeague(List<LeagueResponse.League> leagueList);

}
