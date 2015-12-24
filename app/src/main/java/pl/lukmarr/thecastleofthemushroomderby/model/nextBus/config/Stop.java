package pl.lukmarr.thecastleofthemushroomderby.model.nextBus.config;

/**
 * Created by Łukasz Marczak
 *
 * @since 22.12.15
 */
public class Stop {

    private String tag;
    private String title;
    private String lat;
    private String lon;
    private String stopId;

    public Stop() {
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }
}
