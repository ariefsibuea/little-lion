package com.app.marbola.ui.main.league;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.marbola.R;
import com.app.marbola.data.network.model.LeagueResponse;
import com.app.marbola.di.component.ActivityComponent;
import com.app.marbola.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arief on 11/05/18.
 */

public class LeagueFragment extends BaseFragment implements LeagueMvpView, LeagueAdapter.Callback {

    private static final String TAG = "LeagueFragment";

    @Inject
    LeagueMvpPresenter<LeagueMvpView> mPresenter;

    @Inject
    LeagueAdapter mLeagueAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.league_recycler_view)
    RecyclerView mRecyclerView;

    public static LeagueFragment newInstance() {
        Bundle args = new Bundle();
        LeagueFragment fragment = new LeagueFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_league, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            mLeagueAdapter.setCallback(this);
        }
        return view;
    }

    @Override
    public void updateLeague(List<LeagueResponse.League> leagueList) {
        mLeagueAdapter.addItems(leagueList);
    }

    @Override
    public void onLeagueEmptyViewRetryClick() {
        mPresenter.onViewPrepared();
        Log.d(TAG, "Retry load data schedule");
    }

    @Override
    protected void setUp(View view) {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mLeagueAdapter);

        mPresenter.onViewPrepared();
    }

    @Override
    public void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();

    }
}
