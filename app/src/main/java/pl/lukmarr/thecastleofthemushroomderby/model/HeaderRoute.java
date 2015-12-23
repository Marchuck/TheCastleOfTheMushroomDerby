package pl.lukmarr.thecastleofthemushroomderby.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Łukasz Marczak
 *
 * @since 22.12.15
 */
public class HeaderRoute {
    private String tag;
    private String title;
    private String color;
    private String oppositeColor;
    private double latMin;
    private double latMax;
    private double lonMin;
    private double lonMax;
    private List<Stop> stop = new ArrayList<>();
    private List<Direction> direction = new ArrayList<>();
    private List<Path> path = new ArrayList<>();

    public HeaderRoute(String tag, String title, String color, String oppositeColor, double latMin, double latMax, double lonMin, double lonMax) {
        this.tag = tag;
        this.title = title;
        this.color = color;
        this.oppositeColor = oppositeColor;
        this.latMin = latMin;
        this.latMax = latMax;
        this.lonMin = lonMin;
        this.lonMax = lonMax;
    }

    public HeaderRoute() {
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOppositeColor() {
        return oppositeColor;
    }

    public void setOppositeColor(String oppositeColor) {
        this.oppositeColor = oppositeColor;
    }

    public double getLatMin() {
        return latMin;
    }

    public void setLatMin(double latMin) {
        this.latMin = latMin;
    }

    public double getLatMax() {
        return latMax;
    }

    public void setLatMax(double latMax) {
        this.latMax = latMax;
    }

    public double getLonMin() {
        return lonMin;
    }

    public void setLonMin(double lonMin) {
        this.lonMin = lonMin;
    }

    public double getLonMax() {
        return lonMax;
    }

    public void setLonMax(double lonMax) {
        this.lonMax = lonMax;
    }

    public List<Stop> getStop() {
        return stop;
    }

    public void setStop(List<Stop> stop) {
        this.stop = stop;
    }

    public List<Direction> getDirection() {
        return direction;
    }

    public void setDirection(List<Direction> direction) {
        this.direction = direction;
    }

    public List<Path> getPath() {
        return path;
    }

    public void setPath(List<Path> path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "tag='" + tag + '\'' +
                ", title='" + title + '\'' +
                ", color='" + color + '\'' +
                ", oppositeColor='" + oppositeColor + '\'' +
                ", latMin=" + latMin +
                ", latMax=" + latMax +
                ", lonMin=" + lonMin +
                ", lonMax=" + lonMax +
                ", stop=" + stop.size() +
                ", direction=" + direction.size() +
                ", path=" + path.size() +
                '}';
    }
}
