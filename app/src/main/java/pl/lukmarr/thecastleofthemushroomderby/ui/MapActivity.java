package pl.lukmarr.thecastleofthemushroomderby.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yalantis.starwars.TilesFrameLayout;
import com.yalantis.starwars.interfaces.TilesFrameLayoutListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.lukmarr.thecastleofthemushroomderby.Hero;
import pl.lukmarr.thecastleofthemushroomderby.R;
import pl.lukmarr.thecastleofthemushroomderby.connection.GenericAdapter;
import pl.lukmarr.thecastleofthemushroomderby.connection.StarWarsClient;
import pl.lukmarr.thecastleofthemushroomderby.model.Figure;
import retrofit.RestAdapter;
import retrofit.client.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class MapActivity extends AppCompatActivity {
    public static final String TAG = MapActivity.class.getSimpleName();

    @Bind(R.id.tiles_frame_layout)
    TilesFrameLayout frameLayout;

    @Bind(R.id.character_name)
    TextView textView;

    @Bind(R.id.character_image)
    ImageView imaegView;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @OnClick(R.id.character_image)
    public void oncc() {
        newRequest(++j);
    }

    private void newRequest(int j) {
        client.getCharacter(j)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }).doOnCompleted(new Action0() {
            @Override
            public void call() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }).subscribeOn(Schedulers.trampoline()).observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Hero, Observable<Response>>() {
                    @Override
                    public Observable<Response> call(final Hero hero) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(hero.getName());
                            }
                        });
                        String name = hero.getName().replaceAll(" ", "_");
                        return wikiClient.getCharacterImage(name);
                    }
                }, new Func2<Hero, Response, Figure>() {
                    @Override
                    public Figure call(Hero hero, Response response) {
                        Log.d(TAG, "hero : " + hero);
                        final Figure figure = new Figure();
                        figure.name = hero.getName();
                        figure.image = getImage(response);
                        return figure;
                    }
                }).subscribe(new Action1<Figure>() {
            @Override
            public void call(final Figure figure) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Picasso.with(MapActivity.this).load(figure.image).fit().into(imaegView);
                    }
                });
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(final Throwable throwable) {
                throwable.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        textView.setText(throwable.getMessage());
                        Picasso.with(MapActivity.this)
                                .load(Color.BLACK)
                                .fit()
                                .into(imaegView);
                    }
                });
            }
        });
    }

    int j = 0;
    RestAdapter wikiAdapter;
    StarWarsClient wikiClient;
    StarWarsClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        wikiAdapter = new RestAdapter.Builder().setEndpoint(StarWarsClient.WIKI_ENDPOINT).build();
        wikiClient = wikiAdapter.create(StarWarsClient.class);

        GenericAdapter<Hero> heroGenericAdapter = new GenericAdapter<>(StarWarsClient.SWAPI_ENDPOINT, Hero.class);
        client = heroGenericAdapter.getRestAdapter().create(StarWarsClient.class);
        newRequest(++j);

        frameLayout.setOnAnimationFinishedListener(new TilesFrameLayoutListener() {
            @Override
            public void onAnimationFinished() {
                MapActivity.super.onBackPressed();

            }
        });
    }

    private String getImage(Response result) {
        Log.d(TAG, "getImage ");
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String response = sb.toString();
        Log.d(TAG, response);
        String[] ff = response.split("\"");
        for (String g : ff) {
            if (g.contains("vignette") && g.contains(".jpg")) {
                Log.i(TAG, g);
                return g.replaceAll("amp;", "");
            }
        }
        return "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTEClt0ZSygvxLWsKG-Lrv4klVY0YykkAnbpfIKZVvcSeEj0vyqig";
    }

    @Override
    public void onBackPressed() {
        frameLayout.startAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        frameLayout.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        frameLayout.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
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
