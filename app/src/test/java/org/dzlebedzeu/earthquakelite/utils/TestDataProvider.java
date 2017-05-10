package org.dzlebedzeu.earthquakelite.utils;

import com.google.gson.Gson;

import org.dzlebedzeu.earthquakelite.mvp.model.entities.EarthquakesList;

public class TestDataProvider {

    public static EarthquakesList provideEarthquakesResponseData() {
        return new Gson().fromJson(TestHelper.getStringFromResource("earthquakesresponse.txt"), EarthquakesList.class);
    }
}
