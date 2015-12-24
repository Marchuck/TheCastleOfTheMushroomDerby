package pl.lukmarr.thecastleofthemushroomderby.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.lukmarr.thecastleofthemushroomderby.R;
import pl.lukmarr.thecastleofthemushroomderby.adapters.AgencyAdapter;
import pl.lukmarr.thecastleofthemushroomderby.connection.AdapterConnector;
import pl.lukmarr.thecastleofthemushroomderby.connection.DataCallback;
import pl.lukmarr.thecastleofthemushroomderby.model.nextBus.agency.Agency;
import pl.lukmarr.thecastleofthemushroomderby.model.nextBus.agency.AgencyBody;
import pl.lukmarr.thecastleofthemushroomderby.options.Config;
import pl.lukmarr.thecastleofthemushroomderby.providers.AgenciesProvider;

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
        agencyAdapter = new AgencyAdapter(new ArrayList<Agency>(), new AdapterConnector<Agency>() {
            @Override
            public void onClicked(Agency agency) {
                Config.TAG = agency.getTag();
                startActivity(new Intent(MainActivity.this, RouteChooseActivity.class));
            }
        });
        recyclerView.setAdapter(agencyAdapter);

        if (Config.isNetworkAvailable(this)) {
            downloadAgain();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("NO Internet Connection")
                    .setNegativeButton("Close App", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    }).setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS), 100);
                    dialog.dismiss();
                }
            }).show();
        }
    }

    void downloadAgain() {
        AgenciesProvider.provide(this, progressBar, new DataCallback<AgencyBody>() {
            @Override
            public void onDataReceived(AgencyBody item) {
                agencyAdapter.refresh(item.getAgency());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && Config.isNetworkAvailable(this)) {
            downloadAgain();
        } else {
            Toast.makeText(this, "No internet connection. Closing app.", Toast.LENGTH_LONG).show();
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 600);
        }
    }
}
