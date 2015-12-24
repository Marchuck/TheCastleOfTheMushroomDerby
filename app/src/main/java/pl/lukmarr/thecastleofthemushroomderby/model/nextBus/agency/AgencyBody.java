package pl.lukmarr.thecastleofthemushroomderby.model.nextBus.agency;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Łukasz Marczak
 *
 * @since 23.12.15
 */
public class AgencyBody {
    private List<Agency> agency = new ArrayList<>();
    private String copyright;

    public AgencyBody() {
    }

    public List<Agency> getAgency() {
        return agency;
    }

    public void setAgency(List<Agency> agency) {
        this.agency = agency;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }
}
