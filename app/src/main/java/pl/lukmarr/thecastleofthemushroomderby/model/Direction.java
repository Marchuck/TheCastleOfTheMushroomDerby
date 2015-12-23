package pl.lukmarr.thecastleofthemushroomderby.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Łukasz Marczak
 *
 * @since 22.12.15
 */
public class Direction {
    @SerializedName("tag")
    private String tag;

    @SerializedName("title")
    private String title;

    @SerializedName("name")
    private String name;
    @SerializedName("useForUI")
    private String useForUI;
    @SerializedName("stop")
    private List<StopTag> stop = new ArrayList<>();

    public Direction() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUseForUI() {
        return useForUI;
    }

    public void setUseForUI(String useForUI) {
        this.useForUI = useForUI;
    }

    public List<StopTag> getStop() {
        return stop;
    }

    public void setStop(List<StopTag> stop) {
        this.stop = stop;
    }
}
