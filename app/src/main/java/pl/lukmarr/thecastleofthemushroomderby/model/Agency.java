package pl.lukmarr.thecastleofthemushroomderby.model;

/**
 * Created by Łukasz Marczak
 *
 * @since 19.12.15
 */
public class Agency {
    private String tag;
    private String title;
    private String regionTitle;

    public Agency() {
    }

    public Agency(String tag, String title, String regionTitle) {
        this.tag = tag;
        this.title = title;
        this.regionTitle = regionTitle;
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

    public String getRegionTitle() {
        return regionTitle;
    }

    public void setRegionTitle(String regionTitle) {
        this.regionTitle = regionTitle;
    }

    @Override
    public String toString() {
        return "tag : " + tag +
                ", title : " + title +
                ", regionTitle : " + regionTitle;
    }
}
