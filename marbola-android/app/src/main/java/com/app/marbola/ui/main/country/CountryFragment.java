package com.app.marbola.ui.main.country;

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
import com.app.marbola.data.network.model.CountryResponse;
import com.app.marbola.di.component.ActivityComponent;
import com.app.marbola.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arief on 07/05/18.
 */

public class CountryFragment extends BaseFragment implements CountryMvpView, CountryAdapter.Callback {

    private static final String TAG = "CountryFragment";

    @Inject
    CountryMvpPresenter<CountryMvpView> mPresenter;

    @Inject
    CountryAdapter mCountryAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.country_recycler_view)
    RecyclerView mRecyclerView;

    public static CountryFragment newInstance() {
        Bundle args = new Bundle();
        CountryFragment fragment = new CountryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            mCountryAdapter.setCallback(this);
        }
        return view;
    }

    @Override
    public void updateCountry(List<CountryResponse.Country> countryList) {
        mCountryAdapter.addItems(countryList);
    }

    @Override
    public void onCountryEmptyViewRetryClick() {
        mPresenter.onViewPrepared();
        Log.d(TAG, "Retry load data country");
    }

    @Override
    protected void setUp(View view) {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mCountryAdapter);

        mPresenter.onViewPrepared();
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
