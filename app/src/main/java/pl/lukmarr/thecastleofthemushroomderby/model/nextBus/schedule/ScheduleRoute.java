package pl.lukmarr.thecastleofthemushroomderby.model.nextBus.schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Łukasz Marczak
 *
 * @since 24.12.15
 */
public class ScheduleRoute {
    public String serviceClass;
    public String scheduleClass;
    public String title;
    public String direction;
    public String tag;
    public ScheduleHeader header;
    public List<Tr> tr = new ArrayList<>();
}
