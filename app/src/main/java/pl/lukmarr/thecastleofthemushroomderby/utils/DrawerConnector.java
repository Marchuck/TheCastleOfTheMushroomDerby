package pl.lukmarr.thecastleofthemushroomderby.utils;

/**
 * Thanks Again
 * Created by Łukasz Marczak
 *
 * @since 2015-12-16.
 * Copyright © 2015 SoInteractive S.A. All rights reserved.
 */

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public interface DrawerConnector extends Serializable {
    void openDrawer(LatLng position, String description);

    void closeDrawer();

    boolean isOpened();
}
