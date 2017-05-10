package org.dzlebedzeu.earthquakelite.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.dzlebedzeu.earthquakelite.R;
import org.dzlebedzeu.earthquakelite.ui.ActivityBaseUiController;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Base Activity that contains some common view elements and logic
 */
public class BaseActivity extends AppCompatActivity implements ActivityBaseUiController {

    @BindView(R.id.blocking_wait)
    View mBlockingWait;

    @BindView(R.id.activity_main)
    CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_wrapper);

        ButterKnife.bind(this);
    }

    public void showSpinner() {
        if (mBlockingWait != null) {
            mBlockingWait.setVisibility(View.VISIBLE);
        }
    }

    public void hideSpinner() {
        if (mBlockingWait != null) {
            mBlockingWait.setVisibility(View.GONE);
        }
    }

    @Override
    public CoordinatorLayout getCoordinatorLayout() {
        return mCoordinatorLayout;
    }

    void setContentFragment(Fragment fragment) {
        setContentFragment(fragment, null);
    }

    public void setContentFragment(Fragment fragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (tag != null) {
            ft.add(R.id.content_fragment_container, fragment, tag).commit();
        } else {
            ft.add(R.id.content_fragment_container, fragment).commit();
        }
    }
}
