package org.dzlebedzeu.earthquakelite.mvp.view;

import com.hannesdorfmann.mosby.mvp.MvpView;

import org.dzlebedzeu.earthquakelite.mvp.model.entities.Earthquake;

import java.util.List;

public interface EarthquakesListView extends MvpView {

    void onEarthquakesListLoaded(List<Earthquake> earthquakesList);

    void onNoResults();

    void onError();
}
