package pl.lukmarr.thecastleofthemushroomderby.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Łukasz Marczak
 *
 * @since 22.12.15
 */
public class RouteConfigBody {
    @SerializedName("copyright")
    private String copyright;
    @SerializedName("route")
    private HeaderRoute route;

    public RouteConfigBody() {
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public HeaderRoute getRoute() {
        return route;
    }

    public void setRoute(HeaderRoute route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return copyright + "," + (route == null ? "?" : route.toString());
    }
}
