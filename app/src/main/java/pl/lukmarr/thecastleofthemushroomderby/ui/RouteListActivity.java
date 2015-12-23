package pl.lukmarr.thecastleofthemushroomderby.ui;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.drawer_layout_route_list)
    DrawerLayout drawerLayout;


    @OnClick(R.id.activity_route_list_button_back)
    public void onFabClick() {
        if (helper != null) helper.closeDrawer();
    }

    RightDrawerHelper helper;
    RouteAdapter routeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);
        ButterKnife.bind(this);
        helper = new RightDrawerHelper(this, this, drawerLayout, R.id.activity_route_list_map_fragment);
        routeAdapter = new RouteAdapter(new ArrayList<Route>(), helper);
//        ccrta, Sealine

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(routeAdapter);
        RoutesProvider.xmlParse(progressBar, new ListCallback<Route>() {
            @Override
            public void onListReceived(List<Route> items) {
                routeAdapter.refresh(items);
            }
        });


//        RoutesConfigXMLProvider.getConfig("ccrta", "Sealine", new DataCallback<RouteConfigBody>() {
//            @Override
//            public void onDataReceived(RouteConfigBody item) {
//                Log.d(TAG, "onDataReceived " + item);
//            }
//        });
    }

    @Override
    public void showLoader(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_route_list, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
