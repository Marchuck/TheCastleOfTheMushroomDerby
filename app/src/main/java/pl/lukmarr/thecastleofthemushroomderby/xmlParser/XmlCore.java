package pl.lukmarr.thecastleofthemushroomderby.xmlParser;

import android.support.annotation.Nullable;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Łukasz Marczak
 *
 * @since 20.12.15
 */
public class XmlCore {

    public interface ReadableList<T> {
        List<T> readBody(XmlPullParser parser);
    }

    public interface ReadableObject<T> {
        T readBody(XmlPullParser parser);
    }

    private XmlCore() {
    }

    public static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
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


    @Nullable
    public static <V> List<V> readStreamAsList(InputStream in, ReadableList<V> readableList) {
        try {
            XmlPullParser parser = Xml.newPullParser();
            try {
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                parser.nextTag();
                return readableList.readBody(parser);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                in.close();
            } catch (IOException x) {
                x.printStackTrace();
            }
        }
        return null;
    }

    @Nullable
    public static <V> V readStreamAsObject(InputStream in, ReadableObject<V> readableList) {
        try {
            XmlPullParser parser = Xml.newPullParser();
            try {
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                parser.nextTag();
                return readableList.readBody(parser);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                in.close();
            } catch (IOException x) {
                x.printStackTrace();
            }
        }
        return null;
    }
}
