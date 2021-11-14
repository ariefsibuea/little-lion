package com.app.marbola.ui.main;

import com.app.marbola.ui.base.MvpView;

/**
 * Created by arief on 07/05/18.
 */

public interface MainMvpView extends MvpView {

    void closeNavigationDrawer();

    void lockDrawer();

    void unlockDrawer();

}
