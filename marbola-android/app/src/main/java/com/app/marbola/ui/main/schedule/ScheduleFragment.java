package com.app.marbola.ui.main.schedule;

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
import com.app.marbola.data.network.model.ScheduleResponse;
import com.app.marbola.di.component.ActivityComponent;
import com.app.marbola.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arief on 11/05/18.
 */

public class ScheduleFragment extends BaseFragment implements ScheduleMvpView, ScheduleAdapter.Callback {

    private static final String TAG = "ScheduleFragment";

    @Inject
    ScheduleMvpPresenter<ScheduleMvpView> mPresenter;

    @Inject
    ScheduleAdapter mScheduleAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.schedule_recycler_view)
    RecyclerView mRecyclerView;

    public static ScheduleFragment newInstance() {
        Bundle args = new Bundle();
        ScheduleFragment fragment = new ScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            mScheduleAdapter.setCallback(this);
        }
        return view;
    }

    @Override
    public void updateSchedule(List<ScheduleResponse.Schedule> scheduleList) {
        mScheduleAdapter.addItems(scheduleList);
    }

    @Override
    public void onScheduleEmptyViewRetryClick() {
        mPresenter.onViewPrepared();
        Log.d(TAG, "Retry load data schedule");
    }

    @Override
    protected void setUp(View view) {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mScheduleAdapter);

        mPresenter.onViewPrepared();
    }

    @Override
    public void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
