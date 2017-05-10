package org.dzlebedzeu.earthquakelite.ui;

import android.support.design.widget.CoordinatorLayout;

public interface ActivityBaseUiController {

    void showSpinner();

    void hideSpinner();

    CoordinatorLayout getCoordinatorLayout();
}
