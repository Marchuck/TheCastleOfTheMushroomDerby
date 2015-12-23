package pl.lukmarr.thecastleofthemushroomderby.model;

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

    public Stop(String tag, String title, String lat, String lon, String stopId) {
        this.tag = tag;
        this.title = title;
        this.lat = lat;
        this.lon = lon;
        this.stopId = stopId;
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


    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }
}
