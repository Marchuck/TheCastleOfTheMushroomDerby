package pl.lukmarr.thecastleofthemushroomderby.connection;

/**
 * Created by Łukasz Marczak
 *
 * @since 22.12.15
 */
public interface DataCallback<T> {
    void onDataReceived(T item);
}
