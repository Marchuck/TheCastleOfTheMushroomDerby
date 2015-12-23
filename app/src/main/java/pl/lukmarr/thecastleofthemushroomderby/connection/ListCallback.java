package pl.lukmarr.thecastleofthemushroomderby.connection;

import java.util.List;

/**
 * Created by Łukasz Marczak
 *
 * @since 19.12.15
 */
public interface ListCallback<T> {
    void onListReceived(List<T> items);
}
