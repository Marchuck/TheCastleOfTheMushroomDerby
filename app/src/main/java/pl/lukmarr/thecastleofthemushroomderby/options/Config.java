package pl.lukmarr.thecastleofthemushroomderby.options;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Łukasz Marczak
 *
 * @since 19.12.15
 */
public class Config {
    public static final double MOCK_LATITUDE = 41.5230299;
    public static final double MOCK_LONGITUDE = -70.6698;
    public static String TAG = "ccrta";
    public static boolean RIGHT_DRAWER_TRAFFIC_ENABLED = false;

    public static final String rxJavaError = "rxJavaError";
    public static int transparentLayoutHeight = 550;

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public boolean isConnectingToInternet(Context _context){
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
        }
        return false;
    }
}
