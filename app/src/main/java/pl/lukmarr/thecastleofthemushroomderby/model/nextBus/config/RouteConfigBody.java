package pl.lukmarr.thecastleofthemushroomderby.model.nextBus.config;

/**
 * Created by Łukasz Marczak
 *
 * @since 23.12.15
 */
public class RouteConfigBody {
    private ExtendedRoute route;
    private String copyright;

    public RouteConfigBody() {
    }

    public ExtendedRoute getRoute() {
        return route;
    }

    public void setRoute(ExtendedRoute route) {
        this.route = route;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }
}
