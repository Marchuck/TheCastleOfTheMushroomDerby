package pl.lukmarr.thecastleofthemushroomderby.providers;

import android.util.Log;

import pl.lukmarr.thecastleofthemushroomderby.connection.GenericAdapter;
import pl.lukmarr.thecastleofthemushroomderby.connection.NextBusClient;
import pl.lukmarr.thecastleofthemushroomderby.model.nextBus.busLocation.BusLocationBody;
import pl.lukmarr.thecastleofthemushroomderby.options.Config;
import rx.functions.Action1;

/**
 * Created by Łukasz Marczak
 *
 * @since 24.12.15
 */
public class BusLocationProvider {

    public static void provide(String agencyTag, String routeTag, Action1<BusLocationBody> bodyDataCallback) {
        GenericAdapter<BusLocationBody> busLocationBodyGenericAdapter = new GenericAdapter<>(NextBusClient.ENDPOINT,
                BusLocationBody.class);
        NextBusClient nextBusClient = busLocationBodyGenericAdapter.createBusClient();
        nextBusClient.getBusLocation("vehicleLocations", agencyTag, routeTag, System.currentTimeMillis())
                .subscribe(bodyDataCallback, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(Config.rxJavaError, throwable.getMessage());
                        throwable.printStackTrace();
                    }
                });
    }
}
