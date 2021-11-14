package com.app.marbola.ui.base;

/**
 * Created by arief on 03/05/18.
 */

public interface MvpPresenter<V extends MvpView> {

    void onAttach(V mvpView);

    void onDetach();

    void handleApiError(String error);

}
