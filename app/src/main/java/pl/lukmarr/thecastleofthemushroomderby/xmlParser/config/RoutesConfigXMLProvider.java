package pl.lukmarr.thecastleofthemushroomderby.xmlParser.config;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import pl.lukmarr.thecastleofthemushroomderby.connection.DataCallback;
import pl.lukmarr.thecastleofthemushroomderby.connection.NextBusClient;
import pl.lukmarr.thecastleofthemushroomderby.model.Direction;
import pl.lukmarr.thecastleofthemushroomderby.model.HeaderRoute;
import pl.lukmarr.thecastleofthemushroomderby.model.Path;
import pl.lukmarr.thecastleofthemushroomderby.model.Point;
import pl.lukmarr.thecastleofthemushroomderby.model.Route;
import pl.lukmarr.thecastleofthemushroomderby.model.RouteConfigBody;
import pl.lukmarr.thecastleofthemushroomderby.model.Stop;
import pl.lukmarr.thecastleofthemushroomderby.options.Config;
import pl.lukmarr.thecastleofthemushroomderby.xmlParser.XmlCore;
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
public class RoutesConfigXMLProvider {
    public static final String TAG = RoutesConfigXMLProvider.class.getSimpleName();
    public static String ns = null;

    public static void getConfig(String agencyTag, String routeTag, final DataCallback<RouteConfigBody> callback) {
        Log.d(TAG, "performed call for " + Config.TAG + ", " + routeTag);

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(NextBusClient.ENDPOINT).build();
        NextBusClient nextBusClient = adapter.create(NextBusClient.class);
        nextBusClient.getRouteConfig("routeConfig", agencyTag, routeTag)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Response, RouteConfigBody>() {
                    @Override
                    public RouteConfigBody call(Response response) {
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

    private static RouteConfigBody readStream(InputStream in) {
        return XmlCore.readStreamAsObject(in, new XmlCore.ReadableObject<RouteConfigBody>() {
            @Override
            public RouteConfigBody readBody(XmlPullParser parser) {
                return RoutesConfigXMLProvider.readBody(parser);
            }
        });
    }

    private static RouteConfigBody readBody(XmlPullParser parser) {
        RouteConfigBody config = new RouteConfigBody();
        try {
            parser.require(XmlPullParser.START_TAG, null, "body");

            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) continue;

                String name = parser.getName();
                // Starts by looking for the entry tag
                if (name.equals("route")) {
                    config.setRoute(getHeaderRoute(parser));
                } else {
                    XmlCore.skip(parser);
                }
            }
//            parser.require(XmlPullParser.END_TAG, null, "body");
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return config;
    }

    private static HeaderRoute getHeaderRoute(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "route");
        String mTag = parser.getName();
        Log.d(TAG, "readRoute :  tag name: " + mTag);

        List<Stop> stops = new ArrayList<>();
        List<pl.lukmarr.thecastleofthemushroomderby.model.Path> paths = new ArrayList<>();
        List<Direction> dirs = new ArrayList<>();

        String tag = parser.getAttributeValue(ns, "tag");
        String title = parser.getAttributeValue(ns, "title");
        String color = parser.getAttributeValue(ns, "color");
        String oppositeColor = parser.getAttributeValue(ns, "oppositeColor");
        double latMin = Double.valueOf(parser.getAttributeValue(ns, "latMin"));
        double latMax = Double.valueOf(parser.getAttributeValue(ns, "latMax"));
        double lonMin = Double.valueOf(parser.getAttributeValue(ns, "lonMin"));
        double lonMax = Double.valueOf(parser.getAttributeValue(ns, "lonMax"));
        //do tej pory działało


//        int eventType = parser.getEventType();
//        while (eventType != XmlResourceParser.END_DOCUMENT) {
//            String tagName = parser.getName();
//
//            switch (eventType) {
//                case XmlResourceParser.START_TAG:
//                    if (tagName.equalsIgnoreCase("stop")) {
//                        String tag2 = parser.getAttributeValue(null, "tag");
//                        String title2 = parser.getAttributeValue(null, "title");
//                        String lat =  parser.getAttributeValue(null, "lat" );
//                        String lon =  parser.getAttributeValue(null, "lon" );
//                        String stopId = parser.getAttributeValue(null, "stopId");
//                        stops.add(new Stop(tag2, title2, lat, lon, stopId));
////                        if (parser.getAttributeValue(null, "name").equalsIgnoreCase("MONDAY")) {
////                            int eventType2 = parser.next();
////                            while (eventType2 != XmlResourceParser.END_DOCUMENT) {
////                                String tagName2 = parser.getName();
////                                switch (eventType2) {
////                                    case XmlResourceParser.START_TAG:
////                                        if (tagName2.equalsIgnoreCase("meal")) {
////                                            Log.i("tag", "meal: " + parser.getAttributeValue(null, "name"));
////                                        }
////                                        break;
////                                    case XmlResourceParser.TEXT:
////                                        break;
////                                    case XmlPullParser.END_TAG:
////                                        break;
////                                }
////                                eventType2 = parser.next();
////                            }
////                        }
//                    } else if (tagName.equalsIgnoreCase("direction")) {
//
//                    } else if (tagName.equalsIgnoreCase("path")) {
//                        int newEvent = parser.next();
//                        List<Point> points = new ArrayList<>();
//                        while (newEvent != XmlResourceParser.END_DOCUMENT) {
//                            String newTag = parser.getName();
//                            switch (newEvent) {
//                                case XmlResourceParser.START_TAG: {
//                                    if (newTag.equalsIgnoreCase("point")) {
//                                        double lat = Double.valueOf(parser.getAttributeValue(null, "lat"));
//                                        double lon = Double.valueOf(parser.getAttributeValue(null, "lon"));
//                                        points.add(new Point(lat, lon));
//                                    }
//                                }
//                                default:
//                                    break;
//                            }
//                            newEvent = parser.next();
//                        }
//                        paths.add(new Path(points));
//                    }
//                    break;
//                case XmlResourceParser.TEXT:
//                    break;
//                case XmlPullParser.END_TAG:
//                    break;
//            }
//            eventType = parser.next();
//        }
//        while (parser.next() != XmlPullParser.END_TAG) {
//            if (parser.getEventType() != XmlPullParser.START_TAG) continue;
//
//            String name = parser.getName();
//            // Starts by looking for the entry tag
//            if (name.equals("stop")) {
//                stops.add(getStop(parser));
//            } else if (name.equals("direction")) {
//                Log.i(TAG, "direction detected");
//            } else if (name.equals("path")) {
//                paths.add(newPath(parser));
//            }
//
//        }
//        parser.require(XmlPullParser.END_TAG, ns, "route");
        HeaderRoute route = new HeaderRoute(tag, title, color, oppositeColor, latMin, latMax, lonMin, lonMax);
        route.setStop(stops);
        route.setPath(paths);
        route.setDirection(dirs);
        return route;
    }

    private static Path newPath(XmlPullParser parser) throws IOException, XmlPullParserException {
        Path path = new Path();
        List<Point> points = new ArrayList<>();

        parser.setFeature(Xml.FEATURE_RELAXED, false);
        parser.require(XmlPullParser.START_TAG, ns, "path");
        parser.require(XmlPullParser.START_TAG, ns, "point");
        parser.setFeature(Xml.FEATURE_RELAXED, true);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) continue;

            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("point")) {
                points.add(addNewPoint(parser));
            }
        }
        path.setPoint(points);
        return path;
    }

    private static Point addNewPoint(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.setFeature(Xml.FEATURE_RELAXED, true);
        parser.require(XmlPullParser.START_TAG, ns, "point");
        String mTag = parser.getName();
        Log.d(TAG, "readPoint :  tag name: " + mTag);
        double lat = Double.valueOf(parser.getAttributeValue(null, "lat"));
        double lon = Double.valueOf(parser.getAttributeValue(null, "lon"));
//        parser.setFeature(Xml.FEATURE_RELAXED, false);
        return new Point(lat, lon);
    }

    private static Stop getStop(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.setFeature(Xml.FEATURE_RELAXED, true);
//        parser.require(XmlPullParser.START_TAG, ns, "stop");
        String mTag = parser.getName();
        Log.d(TAG, "readRoute :  tag name: " + mTag);
        String tag = parser.getAttributeValue(null, "tag");
        String title = parser.getAttributeValue(null, "title");
        String lat =  parser.getAttributeValue(null, "lat" );
        String lon =  parser.getAttributeValue(null, "lon" );
        String stopId = parser.getAttributeValue(null, "stopId");
//        parser.require(XmlPullParser.END_TAG, ns, "stop");
        parser.setFeature(Xml.FEATURE_RELAXED, false);
        return new Stop(tag, title, lat, lon, stopId);
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
