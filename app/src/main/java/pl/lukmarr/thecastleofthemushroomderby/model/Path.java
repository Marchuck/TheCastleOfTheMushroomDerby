package pl.lukmarr.thecastleofthemushroomderby.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Łukasz Marczak
 *
 * @since 22.12.15
 */
public class Path {
    @SerializedName("point")
    private List<Point> point = new ArrayList<>();

    public Path() {
    }

    public Path(List<Point> point) {
        this.point = point;
    }

    public List<Point> getPoint() {
        return point;
    }

    public void setPoint(List<Point> point) {
        this.point = point;
    }
}
