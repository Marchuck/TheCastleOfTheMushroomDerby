package pl.lukmarr.thecastleofthemushroomderby.model.nextBus.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Łukasz Marczak
 *
 * @since 22.12.15
 */
public class Direction {
    private String tag;

    private String title;

    private String name;
    private String useForUI;
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
