package pl.lukmarr.thecastleofthemushroomderby.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.lukmarr.thecastleofthemushroomderby.R;
import pl.lukmarr.thecastleofthemushroomderby.adapters.RouteAdapter;
import pl.lukmarr.thecastleofthemushroomderby.connection.AdapterConnector;
import pl.lukmarr.thecastleofthemushroomderby.connection.DataCallback;
import pl.lukmarr.thecastleofthemushroomderby.model.Route;
import pl.lukmarr.thecastleofthemushroomderby.xmlParser.RoutesProvider;

public class RouteListActivity extends AppCompatActivity {
    public static final String TAG = RouteListActivity.class.getSimpleName();
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    RouteAdapter routeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        routeAdapter = new RouteAdapter(new ArrayList<Route>(), new AdapterConnector<Route>() {
            @Override
            public void onClicked(Route element) {
                Log.d(TAG, "onClicked ");
            }
        });
        recyclerView.setAdapter(routeAdapter);
        RoutesProvider.xmlParse(progressBar, new DataCallback<Route>() {
            @Override
            public void onDataReceived(List<Route> items) {
                routeAdapter.refresh(items);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_route_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
