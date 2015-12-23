package pl.lukmarr.thecastleofthemushroomderby.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Łukasz Marczak
 *
 * @since 22.12.15
 */
public class DetailedRoute {
    private HeaderRoute headerRoute;
    private List<Stop> stopList = new ArrayList<>();
    private List<Direction> directionList = new ArrayList<>();
    private List<Path> paths = new ArrayList<>();

    public DetailedRoute() {
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }

    public HeaderRoute getHeaderRoute() {
        return headerRoute;
    }

    public void setHeaderRoute(HeaderRoute headerRoute) {
        this.headerRoute = headerRoute;
    }

    public List<Stop> getStopList() {
        return stopList;
    }

    public void setStopList(List<Stop> stopList) {
        this.stopList = stopList;
    }

    public List<Direction> getDirectionList() {
        return directionList;
    }

    public void setDirectionList(List<Direction> directionList) {
        this.directionList = directionList;
    }

    @Override
    public String toString() {
        return headerRoute +
                ", stopList size = " + stopList.size() +
                ", paths size = " + paths.size() +
                ", directionList size = " + directionList.size();
    }
}
