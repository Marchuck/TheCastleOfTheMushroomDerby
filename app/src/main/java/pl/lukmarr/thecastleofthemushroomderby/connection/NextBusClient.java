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


    /**
     * for fetching agencies: getTransportProviders("agencyList")
     */
    @GET("/publicXMLFeed")
    rx.Observable<Response> getTransportProviders(@Query("command") String command);

    /**
     * command is "routeList", a is agency tag: {@link pl.lukmarr.thecastleofthemushroomderby.options.Config#TAG}
     */
    @GET("/publicXMLFeed")
    rx.Observable<Response> getRouteList(@Query("command") String command, @Query("a") String agencyTag);

    /**
     * command is "routeConfig", a is agency tag: {@link pl.lukmarr.thecastleofthemushroomderby.options.Config#TAG}
     */
    @GET("/publicXMLFeed")
    rx.Observable<Response> getRouteConfig(@Query("command") String command,
                                           @Query("a") String agencyTag, @Query("r") String routeTag);


}
