package pl.lukmarr.thecastleofthemushroomderby.connection;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Łukasz Marczak
 *
 * @since 19.12.15
 */
public interface NextBusClient {
    String ENDPOINT = "http://webservices.nextbus.com/service";//?command=agencyList

    @GET("/publicXMLFeed")
    rx.Observable<Response> getData(@Query("command") String command);

    //for fetching agencies: getData("agencyList")
}
