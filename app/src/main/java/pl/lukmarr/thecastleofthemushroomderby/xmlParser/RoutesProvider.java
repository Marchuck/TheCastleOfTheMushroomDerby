package pl.lukmarr.thecastleofthemushroomderby.xmlParser;

import android.util.Log;
import android.util.Xml;
import android.view.View;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import pl.lukmarr.thecastleofthemushroomderby.connection.ListCallback;
import pl.lukmarr.thecastleofthemushroomderby.connection.NextBusClient;
import pl.lukmarr.thecastleofthemushroomderby.model.Route;
import pl.lukmarr.thecastleofthemushroomderby.options.Config;
import retrofit.RestAdapter;
import retrofit.client.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Łukasz Marczak
 *
 * @since 19.12.15
 */
public class RoutesProvider {
    public static final String TAG = RoutesProvider.class.getSimpleName();
    public static String ns = null;

    public static void xmlParse(final View progressView, final ListCallback<Route> callback) {
        Log.d(TAG, "xmlParse ");
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(NextBusClient.ENDPOINT).build();
        NextBusClient nextBusClient = adapter.create(NextBusClient.class);

        nextBusClient.getRouteList("routeList", Config.TAG)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        progressView.setVisibility(View.VISIBLE);
                    }
                }).doOnCompleted(new Action0() {
            @Override
            public void call() {
                progressView.setVisibility(View.GONE);
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                progressView.setVisibility(View.GONE);
            }
        }).map(new Func1<Response, List<Route>>() {
            @Override
            public List<Route> call(Response response) {
                InputStream in = null;
                try {
                    Log.d(TAG, response.getBody().toString());
                    in = response.getBody().in();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (in != null) return readStream(in);
                return null;
            }
        }).subscribe(new Action1<List<Route>>() {
            @Override
            public void call(List<Route> routes) {
                if (callback != null)
                    callback.onListReceived(routes);
                for (Route route : routes) {
                    Log.d(TAG, "received next route: " + route);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, throwable.getMessage());
                throwable.printStackTrace();
            }
        });
    }


    private static List<Route> readStream(InputStream in) {
        return XmlCore.readStreamAsList(in, new XmlCore.ReadableList<Route>() {
            @Override
            public List<Route> readBody(XmlPullParser parser) {
                return RoutesProvider.readBody(parser);
            }
        });
    }

    private static List<Route> readBody(XmlPullParser parser) {
        List<Route> agencies = new ArrayList<>();
        try {
            parser.require(XmlPullParser.START_TAG, null, "body");

            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                // Starts by looking for the entry tag
                if (name.equals("route")) {
                    agencies.add(readRoute(parser));
                } else {
                    XmlCore.skip(parser);
                }
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return agencies;
    }

    private static Route readRoute(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.setFeature(Xml.FEATURE_RELAXED, true);
        parser.require(XmlPullParser.START_TAG, ns, "route");
        String mTag = parser.getName();
        Log.d(TAG, "readRoute :  tag name: " + mTag);
        String tag = parser.getAttributeValue(null, "tag");
        String title = parser.getAttributeValue(null, "title");
        String subtitle = parser.getAttributeValue(null, "subTitle");
        //parser.require(XmlPullParser.END_TAG,ns,"agency");
        return new Route(tag, title, subtitle);
    }
}
