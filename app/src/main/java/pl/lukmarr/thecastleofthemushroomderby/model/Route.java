package pl.lukmarr.thecastleofthemushroomderby.model;

import android.support.annotation.Nullable;

/**
 * Created by Łukasz Marczak
 *
 * @since 21.12.15
 */
public class Route {
    private String tag;
    private String title;
    @Nullable
    private String shortTitle;

    public Route(String tag, String title, @Nullable String shortTitle) {
        this.tag = tag;
        this.title = title;
        this.shortTitle = shortTitle;
    }

    public Route() {
    }

    @Nullable
    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(@Nullable String shortTitle) {
        this.shortTitle = shortTitle;
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

    @Override
    public String toString() {
        return title + ", " + shortTitle + ", " + tag;
    }
}
