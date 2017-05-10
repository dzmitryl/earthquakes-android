package org.dzlebedzeu.earthquakelite.mvp.model.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EarthquakesList implements Serializable {

    @SerializedName("earthquakes")
    @Expose
    private List<Earthquake> earthquakesList = null;

    public List<Earthquake> getEarthquakesList() {
        return earthquakesList;
    }

    public void setEarthquakesList(List<Earthquake> earthquakesList) {
        this.earthquakesList = earthquakesList;
    }
}
