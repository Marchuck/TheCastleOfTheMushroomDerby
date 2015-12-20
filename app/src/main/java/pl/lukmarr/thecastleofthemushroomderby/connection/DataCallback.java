package pl.lukmarr.thecastleofthemushroomderby.connection;

import java.util.List;

/**
 * Created by Łukasz Marczak
 *
 * @since 19.12.15
 */
public interface DataCallback<T> {
    void onDataReceived(List<T> items);
}
