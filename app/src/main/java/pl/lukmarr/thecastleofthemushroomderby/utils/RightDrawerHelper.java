package pl.lukmarr.thecastleofthemushroomderby.utils;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pl.lukmarr.thecastleofthemushroomderby.model.nextBus.config.ExtendedRoute;
import pl.lukmarr.thecastleofthemushroomderby.options.Config;

/**
 * Thanks Again
 * Created by Łukasz Marczak
 *
 * @since 2015-12-15.
 * Copyright © 2015 SoInteractive S.A. All rights reserved.
 * <p/>
 * Setup googleMapFragment and marker on right drawer, add getters when needed
 */
public class RightDrawerHelper implements DrawerConnector {
    public static final String TAG = RightDrawerHelper.class.getSimpleName();
    private SupportMapFragment mapFragment;
    private GoogleMap tripGoogleMap;
    FragmentActivity activity;
    DrawerLayout drawerLayout;
    Progressive progressive;
    int mapViewHolder;
    @Nullable
    LabelAdapterConnector labelConnector;

    /**
     * @param activity      fragmentActivity for fragment transaction
     * @param progressive  show/hide progressBar while map is loading
     * @param drawerLayout  open/close/check if is opened
     * @param mapViewHolder viewgroup for mapFragment (FrameLayout, RelativeLayout) e.g. R.id.container
     */
    public RightDrawerHelper(FragmentActivity activity, @Nullable Progressive progressive, DrawerLayout drawerLayout, int mapViewHolder) {
        this.activity = activity;
        this.drawerLayout = drawerLayout;
        this.progressive = progressive;
        this.mapViewHolder = mapViewHolder;
    }

    public void setLabelConnector(LabelAdapterConnector connector) {
        this.labelConnector = connector;
    }

    @Override
    public void openDrawer(final ExtendedRoute route) {

        if (labelConnector != null)
            labelConnector.onDrawerOpened(route);


        final LatLng position = route.getPath().get(0).getPoint().get(0).asLatLng();

        //provide NonNull Fragment instance
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
        }

        //attach mapFragment to ViewHolder
        activity.getSupportFragmentManager().beginTransaction().replace(mapViewHolder, mapFragment).commitAllowingStateLoss();
        drawerLayout.openDrawer(Gravity.RIGHT);
        //when map not ready, show progress bar indicator
        if (progressive != null) progressive.showLoader(true);
        //when map is ready, draw marker and zoom GoogleMap
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                if (progressive != null) progressive.showLoader(false);
                tripGoogleMap = googleMap;
                tripGoogleMap.clear();
                tripGoogleMap.setTrafficEnabled(Config.RIGHT_DRAWER_TRAFFIC_ENABLED);
                tripGoogleMap.setMyLocationEnabled(true);
                tripGoogleMap.addMarker(new MarkerOptions()
                                .position(position)
                                .title(route.getTitle())
//                        .icon(categorizedIconDescriptor(categoryCode))
                );
                tripGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(position, 16, 60, 0)));
            }
        });
    }

    @Override
    public void closeDrawer() {
        Log.d(TAG, "closeDrawer ");
        drawerLayout.closeDrawer(Gravity.RIGHT);
    }

    @Override
    public boolean isOpened() {
        Log.d(TAG, "isOpened ");
        return drawerLayout.isDrawerOpen(Gravity.RIGHT);
    }
}
