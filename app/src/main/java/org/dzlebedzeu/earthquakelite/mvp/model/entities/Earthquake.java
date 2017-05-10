package org.dzlebedzeu.earthquakelite.mvp.model.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Earthquake implements Serializable {

    @SerializedName("datetime")
    @Expose
    private String dateTime;

    @SerializedName("depth")
    @Expose
    private Float depth;

    @SerializedName("lng")
    @Expose
    private Float longitude;

    @SerializedName("src")
    @Expose
    private String source;

    @SerializedName("eqid")
    @Expose
    private String earthquakeId;

    @SerializedName("magnitude")
    @Expose
    private Float magnitude;

    @SerializedName("lat")
    @Expose
    private Float latitude;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Float getDepth() {
        return depth;
    }

    public void setDepth(Float depth) {
        this.depth = depth;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getEarthquakeId() {
        return earthquakeId;
    }

    public void setEarthquakeId(String earthquakeId) {
        this.earthquakeId = earthquakeId;
    }

    public Float getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Float magnitude) {
        this.magnitude = magnitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }
}
