package com.app.marbola.ui.main.country;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.marbola.R;
import com.app.marbola.data.network.model.CountryResponse;
import com.app.marbola.ui.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by arief on 07/05/18.
 */

public class CountryAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private Callback mCallback;
    private List<CountryResponse.Country> mCountryResponseList;

    public CountryAdapter(List<CountryResponse.Country> countryResponseList) {
        mCountryResponseList = countryResponseList;
    }

    public void setCallback(Callback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country_view, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mCountryResponseList != null && mCountryResponseList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if (mCountryResponseList != null && mCountryResponseList.size() > 0) {
            return mCountryResponseList.size();
        } else {
            return 1;
        }
    }

    public void addItems(List<CountryResponse.Country> countryList) {
        mCountryResponseList.addAll(countryList);
        notifyDataSetChanged();
    }

    public interface Callback {
        void onCountryEmptyViewRetryClick();
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.id_text_view)
        TextView idTextView;

        @BindView(R.id.country_name_text_view)
        TextView countryNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {
            idTextView.setText("");
            countryNameTextView.setText("");
        }

        public void onBind(int position) {
            super.onBind(position);

            final CountryResponse.Country country = mCountryResponseList.get(position);

            if (country.getId() != 0) {
                idTextView.setText(String.format("%d", country.getId()));
            }

            if (country.getName() != null) {
                countryNameTextView.setText(country.getName());
            }

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
                mCallback.onCountryEmptyViewRetryClick();
            }
        }
    }

}
