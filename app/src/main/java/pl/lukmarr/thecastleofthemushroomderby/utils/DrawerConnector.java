package pl.lukmarr.thecastleofthemushroomderby.utils;

/**
 * Thanks Again
 * Created by Łukasz Marczak
 *
 * @since 2015-12-16.
 * Copyright © 2015 SoInteractive S.A. All rights reserved.
 */

import java.io.Serializable;

import pl.lukmarr.thecastleofthemushroomderby.model.nextBus.config.ExtendedRoute;

public interface DrawerConnector extends Serializable {
    void openDrawer( ExtendedRoute  extendedRoutes);

    void closeDrawer();

    boolean isOpened();
}
