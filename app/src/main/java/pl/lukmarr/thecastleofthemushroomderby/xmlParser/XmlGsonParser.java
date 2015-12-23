package pl.lukmarr.thecastleofthemushroomderby.xmlParser;

import android.util.Log;

import com.stanfy.gsonxml.GsonXml;
import com.stanfy.gsonxml.GsonXmlBuilder;
import com.stanfy.gsonxml.XmlParserCreator;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * Created by Łukasz Marczak
 *
 * @since 22.12.15
 */
public class XmlGsonParser {
    public static final String TAG = XmlGsonParser.class.getSimpleName();

    public static <T> T parse(String xmlAsString, Class<T> klazz) {
        XmlParserCreator parserCreator = new XmlParserCreator() {
            @Override
            public XmlPullParser createParser() {
                try {
                    return XmlPullParserFactory.newInstance().newPullParser();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException("Error! " + e.getMessage());
                }
            }
        };
        GsonXml gsonXml = new GsonXmlBuilder()
                .setXmlParserCreator(parserCreator)
                .create();

//        String xml = "<model><name>my name</name><description>my description</description></model>";
        Log.d(TAG, xmlAsString);
        return gsonXml.fromXml(xmlAsString, klazz);
    }
}
