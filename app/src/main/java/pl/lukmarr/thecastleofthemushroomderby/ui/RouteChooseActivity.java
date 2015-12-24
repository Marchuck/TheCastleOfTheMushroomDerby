package pl.lukmarr.thecastleofthemushroomderby.ui;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.lukmarr.thecastleofthemushroomderby.R;
import pl.lukmarr.thecastleofthemushroomderby.adapters.LabelAdapter;
import pl.lukmarr.thecastleofthemushroomderby.adapters.RouteAdapter;
import pl.lukmarr.thecastleofthemushroomderby.connection.DataCallback;
import pl.lukmarr.thecastleofthemushroomderby.model.nextBus.Route;
import pl.lukmarr.thecastleofthemushroomderby.model.nextBus.RouteBody;
import pl.lukmarr.thecastleofthemushroomderby.options.Config;
import pl.lukmarr.thecastleofthemushroomderby.providers.RoutesProvider;
import pl.lukmarr.thecastleofthemushroomderby.utils.Progressable;
import pl.lukmarr.thecastleofthemushroomderby.utils.RightDrawerHelper;

public class RouteChooseActivity extends AppCompatActivity implements Progressable {
    public static final String TAG = RouteChooseActivity.class.getSimpleName();
    @Bind(R.id.mapLoader)
    ProgressBar mapLoader;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.recyclerView2)
    RecyclerView recyclerView2;
    @Bind(R.id.drawer_layout_route_list)
    DrawerLayout drawerLayout;
    @Bind(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;

    @Bind(R.id.content)
    FrameLayout frejm;

    RightDrawerHelper helper;
    RouteAdapter routeAdapter;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);
        ButterKnife.bind(this);
        helper = new RightDrawerHelper(this, this, drawerLayout, R.id.content);


        routeAdapter = new RouteAdapter(new ArrayList<Route>(), helper);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(routeAdapter);


        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        LabelAdapter labelAdapter = new LabelAdapter(this, frejm, recyclerView2, helper);
        recyclerView2.setAdapter(labelAdapter);
        helper.setLabelConnector(labelAdapter);

        RoutesProvider.provide(Config.TAG, new DataCallback<RouteBody>() {
            @Override
            public void onDataReceived(RouteBody item) {
                routeAdapter.refresh(item.getRoute());
            }
        });

        initDrawer();
    }

    private void initDrawer() {
        Log.d(TAG, "initDrawer() called with: " + "");

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, android.R.string.ok, android.R.string.no) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }
        }; // drawerLayout Toggle Object Made
        drawerLayout.setDrawerListener(toggle); // drawerLayout Listener set to the drawerLayout toggle
        toggle.syncState();
        drawerLayout.closeDrawer(Gravity.RIGHT);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void showLoader(boolean show) {
        int v = show ? View.VISIBLE : View.GONE;
        mapLoader.setVisibility(v);
    }

}
