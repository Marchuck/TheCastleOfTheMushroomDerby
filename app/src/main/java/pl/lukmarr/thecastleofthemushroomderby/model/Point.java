package pl.lukmarr.thecastleofthemushroomderby.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Łukasz Marczak
 *
 * @since 22.12.15
 */
public class Point {
    @SerializedName("lat")
    double lat;
    @SerializedName("lon")
    double lon;

    public Point() {
    }

    public Point(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
