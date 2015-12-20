package pl.lukmarr.thecastleofthemushroomderby;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.lukmarr.thecastleofthemushroomderby.adapters.AgencyAdapter;
import pl.lukmarr.thecastleofthemushroomderby.connection.AgencyConnector;
import pl.lukmarr.thecastleofthemushroomderby.connection.DataCallback;
import pl.lukmarr.thecastleofthemushroomderby.model.Agency;
import pl.lukmarr.thecastleofthemushroomderby.options.Config;
import pl.lukmarr.thecastleofthemushroomderby.ui.MapActivity;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    AgencyAdapter agencyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate ");
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        agencyAdapter = new AgencyAdapter(new ArrayList<Agency>(), new AgencyConnector() {
            @Override
            public void onClicked(Agency agency) {
                Config.TAG = agency.getTag();
                Intent d = new Intent(MainActivity.this, MapActivity.class);
                startActivity(d);
            }
        });
        recyclerView.setAdapter(agencyAdapter);

        AgenciesProvider.xmlParse(progressBar, new DataCallback<Agency>() {
            @Override
            public void onDataReceived(final List<Agency> items) {
                agencyAdapter.refresh(items);
            }
        });
    }
}
