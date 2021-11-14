package com.app.marbola.ui.main.league;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.marbola.R;
import com.app.marbola.data.network.model.LeagueResponse;
import com.app.marbola.ui.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by arief on 11/05/18.
 */

public class LeagueAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private LeagueAdapter.Callback mCallback;
    private List<LeagueResponse.League> mLeagueResponseList;

    public LeagueAdapter(List<LeagueResponse.League> leagueResponseList) {
        this.mLeagueResponseList = leagueResponseList;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new LeagueAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_league_view, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new LeagueAdapter.EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if (mLeagueResponseList != null && mLeagueResponseList.size() > 0) {
            return mLeagueResponseList.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mLeagueResponseList != null && mLeagueResponseList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    public void addItems(List<LeagueResponse.League> leagueList) {
        mLeagueResponseList.addAll(leagueList);
        notifyDataSetChanged();
    }

    public void setCallback(LeagueAdapter.Callback callback) {
        this.mCallback = callback;
    }

    public interface Callback {
        void onLeagueEmptyViewRetryClick();
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.league_name_text_view)
        TextView leagueNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {
            leagueNameTextView.setText("");
        }

        public void onBind(int position) {
            super.onBind(position);

            final LeagueResponse.League league = mLeagueResponseList.get(position);

            if (!league.getName().equalsIgnoreCase(null)) {
                leagueNameTextView.setText(String.format("%s", league.getName()));
            }
        }

    }

    public class EmptyViewHolder extends BaseViewHolder {

        @BindView(R.id.btn_retry)
        Button retryButton;

        @BindView(R.id.tv_message)
        TextView messageTextView;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }

        @OnClick(R.id.btn_retry)
        void onRetryClick() {
            if (mCallback != null) {
                mCallback.onLeagueEmptyViewRetryClick();
            }
        }
    }

}
