package pl.lukmarr.thecastleofthemushroomderby;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import pl.lukmarr.thecastleofthemushroomderby.connection.NextBusClient;
import pl.lukmarr.thecastleofthemushroomderby.model.Agency;
import retrofit.RestAdapter;
import retrofit.client.Response;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Łukasz Marczak
 *
 * @since 19.12.15
 */
public class RxParser {
    public static final String TAG = RxParser.class.getSimpleName();

    public static void xmlParse() {
        Log.d(TAG, "xmlParse ");
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(NextBusClient.ENDPOINT).build();
        NextBusClient nextBusClient = adapter.create(NextBusClient.class);

        nextBusClient.getData("agencyList").map(new Func1<Response, List<Agency>>() {
            @Override
            public List<Agency> call(Response response) {
                InputStream in = null;
                XmlPullParser parser = null;
                try {
                    Log.d(TAG, response.getBody().toString());
                    in = response.getBody().in();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (in != null) {
                    try {
                        parser = Xml.newPullParser();
                        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                        parser.setInput(in, null);
                        parser.nextTag();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    List<Agency> agencies = new ArrayList<>();
                    if (parser != null) {
                        try {
                            parser.require(XmlPullParser.START_TAG, null, "body");

                            while (parser.next() != XmlPullParser.END_TAG) {
                                if (parser.getEventType() != XmlPullParser.START_TAG) {
                                    continue;
                                }
                                String name = parser.getName();
                                // Starts by looking for the entry tag
                                if (name.equals("agency")) {
                                    agencies.add(readAgency(parser));
                                } else {
                                    skip(parser);
                                }
                            }
                        } catch (IOException | XmlPullParserException e) {
                            e.printStackTrace();
                        }

                    }
                    return agencies;
                }
                return null;
            }
        }).subscribe(new Action1<List<Agency>>() {
            @Override
            public void call(List<Agency> agencies) {
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

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
    private static Agency readAgency(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "agency");
        String tag = null;
        String title = null;
        String regionTitle = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("tag")) {
                tag = readField(parser, "tag");
            } else if (name.equals("title")) {
                title = readField(parser, "title");
            } else if (name.equals("regionTitle")) {
                regionTitle = readField(parser, "regionTitle");
            } else {
                skip(parser);
            }
        }
        return new Agency(tag, title, regionTitle);
    }

    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private static String readField(XmlPullParser parser, String key) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, key);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, key);
        return title;
    }
}
