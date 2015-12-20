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

import pl.lukmarr.thecastleofthemushroomderby.connection.DataCallback;
import pl.lukmarr.thecastleofthemushroomderby.connection.NextBusClient;
import pl.lukmarr.thecastleofthemushroomderby.model.Agency;
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
public class AgenciesProvider {
    public static final String TAG = AgenciesProvider.class.getSimpleName();
    public static String ns = null;

    public static void xmlParse(final View progressView, final DataCallback<Agency> callback) {
        Log.d(TAG, "xmlParse ");
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(NextBusClient.ENDPOINT).build();
        NextBusClient nextBusClient = adapter.create(NextBusClient.class);

        nextBusClient.getData("agencyList")
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
        }).map(new Func1<Response, List<Agency>>() {
            @Override
            public List<Agency> call(Response response) {
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
        }).subscribe(new Action1<List<Agency>>() {
            @Override
            public void call(List<Agency> agencies) {
                if (callback != null)
                    callback.onDataReceived(agencies);
                for (Agency agency : agencies) {
                    Log.d(TAG, "received next agency: " + agency);
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


    private static List<Agency> readStream(InputStream in) {
        return XmlCore.readStream(in, new XmlCore.Readable<Agency>() {
            @Override
            public List<Agency> readBody(XmlPullParser parser) {
                return AgenciesProvider.readBody(parser);
            }
        });
    }

    private static List<Agency> readBody(XmlPullParser parser) {
        List<Agency> agencies = new ArrayList<>();
        try {
            parser.require(XmlPullParser.START_TAG, null, "body");

            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                // Starts by looking for the entry tag
                if (name.equals("agency")) {
                    agencies.add(readAgency(parser));
                } else {
                    XmlCore.skip(parser);
                }
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return agencies;
    }


    private static Agency readAgency(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.setFeature(Xml.FEATURE_RELAXED, true);
        parser.require(XmlPullParser.START_TAG, ns, "agency");
        String mTag = parser.getName();
        Log.d(TAG, "readAgency :  tag name: " + mTag);
        String tag = parser.getAttributeValue(null, "tag");
        String title = parser.getAttributeValue(null, "title");
        String regionTitle = parser.getAttributeValue(null, "regionTitle");
        //parser.require(XmlPullParser.END_TAG,ns,"agency");
        return new Agency(tag, title, regionTitle);
    }
}
