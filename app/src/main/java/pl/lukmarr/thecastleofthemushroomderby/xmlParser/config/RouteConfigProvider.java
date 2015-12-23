package pl.lukmarr.thecastleofthemushroomderby.xmlParser.config;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import pl.lukmarr.thecastleofthemushroomderby.connection.DataCallback;
import pl.lukmarr.thecastleofthemushroomderby.connection.NextBusClient;
import pl.lukmarr.thecastleofthemushroomderby.model.RouteConfigBody;
import pl.lukmarr.thecastleofthemushroomderby.options.Config;
import pl.lukmarr.thecastleofthemushroomderby.xmlParser.XmlGsonParser;
import retrofit.RestAdapter;
import retrofit.client.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Łukasz Marczak
 *
 * @since 19.12.15
 */
public class RouteConfigProvider {
    public static final String TAG = RouteConfigProvider.class.getSimpleName();

    public static void getConfig(String agencyTag, String routeTag, final DataCallback<RouteConfigBody> callback) {
        Log.d(TAG, "performed call for " + Config.TAG + ", " + routeTag);

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(NextBusClient.ENDPOINT).build();
        NextBusClient nextBusClient = adapter.create(NextBusClient.class);
        nextBusClient.getRouteConfig("routeConfig", agencyTag, routeTag)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Response, String>() {
                    @Override
                    public String call(Response response) {
                        return response2String(response);
                    }
                }).map(new Func1<String, RouteConfigBody>() {
            @Override
            public RouteConfigBody call(String s) {
                return XmlGsonParser.parse(s, RouteConfigBody.class);
            }
        }).subscribe(new Action1<RouteConfigBody>() {
            @Override
            public void call(RouteConfigBody routeConfigBody) {
                callback.onDataReceived(routeConfigBody);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, throwable.getMessage());
                throwable.printStackTrace();
            }
        });
    }

    static String response2String(retrofit.client.Response response) {
        BufferedReader reader;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = sb.toString();
        Log.d(TAG, result);
        return result;
    }
}
