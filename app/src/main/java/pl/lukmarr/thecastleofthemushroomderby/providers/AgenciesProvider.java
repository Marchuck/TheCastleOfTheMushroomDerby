package pl.lukmarr.thecastleofthemushroomderby.providers;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import pl.lukmarr.thecastleofthemushroomderby.connection.DataCallback;
import pl.lukmarr.thecastleofthemushroomderby.connection.GenericAdapter;
import pl.lukmarr.thecastleofthemushroomderby.connection.NextBusClient;
import pl.lukmarr.thecastleofthemushroomderby.connection.UIAction;
import pl.lukmarr.thecastleofthemushroomderby.connection.UIError;
import pl.lukmarr.thecastleofthemushroomderby.model.nextBus.agency.AgencyBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Łukasz Marczak
 *
 * @since 19.12.15
 */
public class AgenciesProvider {
    public static final String TAG = AgenciesProvider.class.getSimpleName();


    public static void provide(final Activity a ,final View v , final DataCallback<AgencyBody> callback) {
        GenericAdapter<AgencyBody> adapter = new GenericAdapter<>(NextBusClient.ENDPOINT, AgencyBody.class);
        NextBusClient nextBusClient = adapter.createBusClient();

        nextBusClient.getTransportProviders("agencyList")
                .doOnSubscribe(new UIAction(a,v,true))
                .doOnError(new UIError(a))
                .doOnCompleted(new UIAction(a, v, false))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<AgencyBody>() {
                    @Override
                    public void call(AgencyBody agencies) {
                        callback.onDataReceived(agencies);
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
