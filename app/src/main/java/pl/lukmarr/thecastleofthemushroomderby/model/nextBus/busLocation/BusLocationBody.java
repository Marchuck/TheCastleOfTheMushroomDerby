package pl.lukmarr.thecastleofthemushroomderby.model.nextBus.busLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Łukasz Marczak
 *
 * @since 24.12.15
 */
public class BusLocationBody {

    public List<Vehicle> vehicle = new ArrayList<Vehicle>();
    public LastTime lastTime;
    public String copyright;

}