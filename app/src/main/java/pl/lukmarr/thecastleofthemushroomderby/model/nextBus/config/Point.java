package pl.lukmarr.thecastleofthemushroomderby.model.nextBus.config;

import com.google.android.gms.maps.model.LatLng;

import pl.lukmarr.thecastleofthemushroomderby.options.Config;

/**
 * Created by Łukasz Marczak
 *
 * @since 22.12.15
 */
public class Point {
    private String lat;
    private String lon;

    public LatLng asLatLng() {
        try {
            double la = Double.valueOf(lat);
            double lo = Double.valueOf(lon);
            return new LatLng(la, lo);
        } catch (NumberFormatException e) {
            return new LatLng(Config.MOCK_LATITUDE, Config.MOCK_LONGITUDE);
        }
    }

    public Point(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
