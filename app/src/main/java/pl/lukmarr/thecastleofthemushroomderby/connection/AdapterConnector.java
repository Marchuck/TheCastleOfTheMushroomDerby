package pl.lukmarr.thecastleofthemushroomderby.connection;

/**
 * Created by Łukasz Marczak
 *
 * @since 19.12.15
 */
public interface AdapterConnector<T> {
    void onClicked(T element);
}
