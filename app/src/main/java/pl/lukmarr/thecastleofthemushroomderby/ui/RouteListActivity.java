package pl.lukmarr.thecastleofthemushroomderby.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.lukmarr.thecastleofthemushroomderby.R;
import pl.lukmarr.thecastleofthemushroomderby.adapters.RouteAdapter;
import pl.lukmarr.thecastleofthemushroomderby.connection.ListCallback;
import pl.lukmarr.thecastleofthemushroomderby.model.Route;
import pl.lukmarr.thecastleofthemushroomderby.utils.Progressable;
import pl.lukmarr.thecastleofthemushroomderby.utils.RightDrawerHelper;
import pl.lukmarr.thecastleofthemushroomderby.xmlParser.RoutesProvider;

public class RouteListActivity extends AppCompatActivity implements Progressable {
    public static final String TAG = RouteListActivity.class.getSimpleName();
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.mapLoader)
    ProgressBar mapLoader;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.drawer_layout_route_list)
    DrawerLayout drawerLayout;
    @Bind(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;

    @Bind(R.id.activity_route_list_button_back)
    public FloatingActionButton fab;

    @OnClick(R.id.activity_route_list_button_back)
    public void onFabClick() {
        if (drawerLayout != null)
            drawerLayout.closeDrawer(Gravity.RIGHT);
    }

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
//        ccrta, Sealine

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(routeAdapter);
        RoutesProvider.xmlParse(progressBar, new ListCallback<Route>() {
            @Override
            public void onListReceived(List<Route> items) {
                Log.d(TAG, "onListReceived " + items.size());
                routeAdapter.refresh(items);
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
//        progressBar.setVisibility(v);
        mapLoader.setVisibility(v);
    }

}
