package com.app.marbola.ui.main.country;

import com.app.marbola.data.network.model.CountryResponse;
import com.app.marbola.ui.base.MvpView;

import java.util.List;

/**
 * Created by arief on 07/05/18.
 */

public interface CountryMvpView extends MvpView {

    void updateCountry(List<CountryResponse.Country> countryList);

}
