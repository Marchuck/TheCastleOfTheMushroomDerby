package pl.lukmarr.thecastleofthemushroomderby.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Łukasz Marczak
 *
 * @since 22.12.15
 */
public class StopTag {

    @SerializedName("tag")
    private String tag;

    public StopTag() {
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
