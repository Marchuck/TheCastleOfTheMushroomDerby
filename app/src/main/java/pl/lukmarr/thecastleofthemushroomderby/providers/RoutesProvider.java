package pl.lukmarr.thecastleofthemushroomderby.providers;

import android.util.Log;

import pl.lukmarr.thecastleofthemushroomderby.connection.DataCallback;
import pl.lukmarr.thecastleofthemushroomderby.connection.GenericAdapter;
import pl.lukmarr.thecastleofthemushroomderby.connection.NextBusClient;
import pl.lukmarr.thecastleofthemushroomderby.model.nextBus.RouteBody;
import pl.lukmarr.thecastleofthemushroomderby.model.nextBus.RouteConfigBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Łukasz Marczak
 *
 * @since 19.12.15
 */
public class RoutesProvider {
    public static final String TAG = RoutesProvider.class.getSimpleName();
    public static String ns = null;

    public static  void provide(String agencyTag, final DataCallback<RouteBody> callback) {
        GenericAdapter<RouteConfigBody> adapter = new GenericAdapter<>(NextBusClient.ENDPOINT, RouteConfigBody.class);
        NextBusClient nextBusClient = adapter.createBusClient();
        nextBusClient.getRouteList("routeList", agencyTag)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RouteBody>() {
                    @Override
                    public void call(RouteBody routeBody) {
                        callback.onDataReceived(routeBody);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, throwable.getMessage());
                        callback.onDataReceived(null);
                    }
                });
    }
}
