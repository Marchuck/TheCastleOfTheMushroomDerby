package pl.lukmarr.thecastleofthemushroomderby.xmlParser;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Łukasz Marczak
 *
 * @since 22.12.15
 */
public class ResponseAsXmlStringProvider {
    public static final String TAG = ResponseAsXmlStringProvider.class.getSimpleName();

    public static String response2String(retrofit.client.Response response) {
        BufferedReader reader;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = sb.toString();
        Log.d(TAG, result);
        return result;
    }
}
