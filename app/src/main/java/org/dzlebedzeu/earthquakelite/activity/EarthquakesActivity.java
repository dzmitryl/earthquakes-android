package org.dzlebedzeu.earthquakelite.activity;

import android.os.Bundle;

import org.dzlebedzeu.earthquakelite.fragment.EarthquakesListFragment;
import org.dzlebedzeu.earthquakelite.mvp.model.entities.Earthquake;

/**
 * Main screen activity
 */
public class EarthquakesActivity extends BaseActivity
        implements EarthquakesListFragment.OnEarthquakesListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            EarthquakesListFragment newFragment = EarthquakesListFragment.newInstance();
            setContentFragment(newFragment);
        }
    }

    @Override
    public void onEarthquakeSelected(Earthquake earthquake) {
        //todo: implement
    }
}
