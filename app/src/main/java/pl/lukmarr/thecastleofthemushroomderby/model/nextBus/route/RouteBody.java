package pl.lukmarr.thecastleofthemushroomderby.model.nextBus.route;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Łukasz Marczak
 *
 * @since 23.12.15
 */
public class RouteBody {
    private String copyright;
    private List<Route> route = new ArrayList<Route>();

    public RouteBody() {
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public List<Route> getRoute() {
        return route;
    }

    public void setRoute(List<Route> route) {
        this.route = route;
    }
}
