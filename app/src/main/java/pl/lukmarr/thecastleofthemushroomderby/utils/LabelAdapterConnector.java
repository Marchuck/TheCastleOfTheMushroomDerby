package pl.lukmarr.thecastleofthemushroomderby.utils;

import pl.lukmarr.thecastleofthemushroomderby.model.nextBus.config.ExtendedRoute;

/**
 * Created by Łukasz Marczak
 *
 * @since 24.12.15
 */
public interface LabelAdapterConnector {
    void onDrawerOpened(ExtendedRoute route);
}
