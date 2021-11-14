package com.app.marbola.ui.main.schedule;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.marbola.R;
import com.app.marbola.data.network.model.ScheduleResponse;
import com.app.marbola.ui.base.BaseViewHolder;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by arief on 11/05/18.
 */

public class ScheduleAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private ScheduleAdapter.Callback mCallback;
    private List<ScheduleResponse.Schedule> mScheduleResponseList;
    private List<String> mLeagueList = new ArrayList<String>();

    public ScheduleAdapter(List<ScheduleResponse.Schedule> scheduleResponseList) {
        mScheduleResponseList = scheduleResponseList;
        for (ScheduleResponse.Schedule item : scheduleResponseList) {
            mLeagueList.add(item.getLeagueName());
        }
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ScheduleAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_view, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new ScheduleAdapter.EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if (mScheduleResponseList != null && mScheduleResponseList.size() > 0) {
            return mScheduleResponseList.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mScheduleResponseList != null && mScheduleResponseList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    public void addItems(List<ScheduleResponse.Schedule> scheduleList) {
        mScheduleResponseList.addAll(scheduleList);
        addLeagueItems(scheduleList);
        notifyDataSetChanged();
    }

    private void addLeagueItems(List<ScheduleResponse.Schedule> scheduleList) {
        for (ScheduleResponse.Schedule mItem : scheduleList) {
            mLeagueList.add(mItem.getLeagueName());
        }
        Set<String> setLeagueItems = new HashSet<String>(mLeagueList);
        mLeagueList.clear();
        mLeagueList.addAll(setLeagueItems);
    }

    public interface Callback {
        void onScheduleEmptyViewRetryClick();
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.schedule_home_team_image_view)
        ImageView homeLogoImageView;

        @BindView(R.id.schedule_away_team_image_view)
        ImageView awayLogoImageView;

        @BindView(R.id.schedule_score_home_text_view)
        TextView homeScoreTextView;

        @BindView(R.id.schedule_score_away_text_view)
        TextView awayScoreTextView;

        @BindView(R.id.schedule_score_delimiter)
        TextView scoreDelimiter;

        @BindView(R.id.league_text_view)
        TextView leagueTextView;

        @BindView(R.id.date_time_text_view)
        TextView dateTimeTextView;

        @BindView(R.id.schedule_home_team_text_view)
        TextView homeTeamTextView;

        @BindView(R.id.schedule_away_team_text_view)
        TextView awayTeamTextView;

        /**
         * Uncomment if you want to show prediction on the card view
         */
        /*
        @BindView(R.id.schedule_predict_result)
        TextView predictResult;
        */
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {
            leagueTextView.setText("");
            dateTimeTextView.setText("");
            homeTeamTextView.setText("");
            awayTeamTextView.setText("");
            homeLogoImageView.setImageDrawable(itemView.getResources().getDrawable(R.drawable.logo_team_1));
            awayLogoImageView.setImageDrawable(itemView.getResources().getDrawable(R.drawable.logo_team_2));
            homeScoreTextView.setText("?");
            awayScoreTextView.setText("?");
            scoreDelimiter.setText(R.string.score_delimiter);
            /* predictResult.setText(R.string.predict_none); */
        }

        public void onBind(int position) {
            super.onBind(position);

            final ScheduleResponse.Schedule schedule = mScheduleResponseList.get(position);

            if (!schedule.getLeagueName().equalsIgnoreCase(null)) {
                leagueTextView.setText(String.format("%s", schedule.getLeagueName()));
            }

            if (!schedule.getDateSchedule().equalsIgnoreCase(null) && !schedule.getTimeSchedule().equalsIgnoreCase(null)) {
                dateTimeTextView.setText(String.format("%s %s", schedule.getDateSchedule(), schedule.getTimeSchedule()));
            }

            if (schedule.getHomeTeamLogoUrl() != null) {
                Glide.with(itemView.getContext())
                        .load(schedule.getHomeTeamLogoUrl())
                        .asBitmap()
                        .into(homeLogoImageView);
            } else {
                homeLogoImageView.setImageDrawable(itemView.getResources().getDrawable(R.drawable.logo_team_1));
            }

            if (schedule.getAwayTeamLogoUrl() != null) {
                Glide.with(itemView.getContext())
                        .load(schedule.getAwayTeamLogoUrl())
                        .asBitmap()
                        .into(awayLogoImageView);
            } else {
                homeLogoImageView.setImageDrawable(itemView.getResources().getDrawable(R.drawable.logo_team_2));
            }

            if (!schedule.getHomeTeamName().equalsIgnoreCase(null)) {
                homeTeamTextView.setText(String.format("%s", schedule.getHomeTeamName()));
            }

            if (!schedule.getAwayTeamName().equalsIgnoreCase(null)) {
                awayTeamTextView.setText(String.format("%s", schedule.getAwayTeamName()));
            }

            /**
             * Uncomment if you want to show prediction on the card view
             */
            /*
            if (schedule.getPredictionResult() == 0) {
                predictResult.setText(String.format("%s", "Draw"));
            } else if (schedule.getPredictionResult() == 1) {
                predictResult.setText(String.format("%s", "Home Team Win"));
            } else if (schedule.getPredictionResult() == 2) {
                predictResult.setText(String.format("%s", "Away Team Win"));
            } else {
                predictResult.setText(String.format("%s", "None"));
            }
            */

            homeScoreTextView.setText("?");
            awayScoreTextView.setText("?");
            scoreDelimiter.setText(R.string.score_delimiter);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
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
                mCallback.onScheduleEmptyViewRetryClick();
            }
        }
    }

}
