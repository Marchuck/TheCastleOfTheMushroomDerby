package pl.lukmarr.thecastleofthemushroomderby.providers;

import android.util.Log;

import pl.lukmarr.thecastleofthemushroomderby.connection.DataCallback;
import pl.lukmarr.thecastleofthemushroomderby.connection.GenericAdapter;
import pl.lukmarr.thecastleofthemushroomderby.connection.NextBusClient;
import pl.lukmarr.thecastleofthemushroomderby.model.nextBus.RouteConfigBody;
import pl.lukmarr.thecastleofthemushroomderby.options.Config;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Łukasz Marczak
 *
 * @since 23.12.15
 */
public class RouteConfigProvider {

    public static void provide(String agencyTag, String routeTag, final DataCallback<RouteConfigBody> callback) {
        GenericAdapter<RouteConfigBody> adapter = new GenericAdapter<>(NextBusClient.ENDPOINT, RouteConfigBody.class);
        NextBusClient nextBusClient = adapter.createBusClient();
        nextBusClient.getRouteConfig("routeConfig", agencyTag, routeTag)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RouteConfigBody>() {
                    @Override
                    public void call(RouteConfigBody routeConfigBody) {
                        callback.onDataReceived(routeConfigBody);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(Config.rxJavaError, throwable.getMessage());
                        callback.onDataReceived(null);
                    }
                });
    }
}
