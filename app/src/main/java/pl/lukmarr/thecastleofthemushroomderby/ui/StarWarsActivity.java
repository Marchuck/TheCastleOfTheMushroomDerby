package pl.lukmarr.thecastleofthemushroomderby.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import pl.lukmarr.thecastleofthemushroomderby.model.starwars.Figure;
import retrofit.RestAdapter;
import retrofit.client.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

public class StarWarsActivity extends AppCompatActivity {
    public static final String TAG = StarWarsActivity.class.getSimpleName();
    long time;
    int j = 0;
    RestAdapter wikiAdapter;
    StarWarsClient wikiClient;
    StarWarsClient client;
    public StarWarsActivity() {
        super();
        Log.d(TAG, "StarWarsActivity() ");
        time = System.currentTimeMillis();
        setupAdapters();
        subjectRequest(++j);
    }
    private Subject<Figure, Figure> mSubject = PublishSubject.create();

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
        time = System.currentTimeMillis();
        newRequest(++j);
    }

    private void subjectRequest(int j) {
        client.getCharacter(j)
                .subscribeOn(Schedulers.trampoline()).observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Hero, Observable<Response>>() {
                    @Override
                    public Observable<Response> call(final Hero hero) {
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
                }).subscribe(mSubject);
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
                        textView.setText(figure.name);
                        Log.d(TAG, "time elapsed: " + (System.currentTimeMillis() - time));
                        Picasso.with(StarWarsActivity.this).load(figure.image).fit().into(imaegView);
                    }
                });
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(final Throwable throwable) {
                throwable.printStackTrace();
                Log.e(TAG, "time elapsed: " + (System.currentTimeMillis() - time));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        textView.setText(throwable.getMessage());
                        Picasso.with(StarWarsActivity.this)
                                .load(Color.BLACK)
                                .fit()
                                .into(imaegView);
                    }
                });
            }
        });
    }


    void setupAdapters() {
        wikiAdapter = new RestAdapter.Builder().setEndpoint(StarWarsClient.WIKI_ENDPOINT).build();
        wikiClient = wikiAdapter.create(StarWarsClient.class);
        GenericAdapter<Hero> heroGenericAdapter = new GenericAdapter<>(StarWarsClient.SWAPI_ENDPOINT, Hero.class);
        client = heroGenericAdapter.getRestAdapter().create(StarWarsClient.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        mSubject.subscribe(new Action1<Figure>() {
            @Override
            public void call(final Figure figure) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(figure.name);
                        Log.d(TAG, "time elapsed: " + (System.currentTimeMillis() - time));
                        Picasso.with(StarWarsActivity.this).load(figure.image).fit().into(imaegView);
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
                        Picasso.with(StarWarsActivity.this)
                                .load(Color.BLACK)
                                .fit()
                                .into(imaegView);
                    }
                });
            }
        });
        frameLayout.setOnAnimationFinishedListener(new TilesFrameLayoutListener() {
            @Override
            public void onAnimationFinished() {
                StarWarsActivity.super.onBackPressed();

            }
        });
    }

    private String getImage(Response response1) {
        Log.d(TAG, "getImage ");
        BufferedReader reader;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(response1.getBody().in()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = sb.toString();
        Log.d(TAG, result);
        String[] ff = result.split("\"");
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
}
