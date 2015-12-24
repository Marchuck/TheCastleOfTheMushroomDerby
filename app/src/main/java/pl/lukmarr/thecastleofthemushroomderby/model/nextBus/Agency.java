package pl.lukmarr.thecastleofthemushroomderby.model.nextBus;

/**
 * Created by Łukasz Marczak
 *
 * @since 19.12.15
 */
public class Agency {
    private String title;
    private String regionTitle;
    private String tag;
    private String shortTitle;

    public Agency() {
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }
}
