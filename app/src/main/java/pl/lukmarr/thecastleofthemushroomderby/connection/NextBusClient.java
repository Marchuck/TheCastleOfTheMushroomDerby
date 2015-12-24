package pl.lukmarr.thecastleofthemushroomderby.connection;

import pl.lukmarr.thecastleofthemushroomderby.model.nextBus.AgencyBody;
import pl.lukmarr.thecastleofthemushroomderby.model.nextBus.RouteBody;
import pl.lukmarr.thecastleofthemushroomderby.model.nextBus.RouteConfigBody;
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
    @GET("/publicJSONFeed")
    rx.Observable<AgencyBody> getTransportProviders(@Query("command") String command);

    /**
     * command is "routeList", a is agency tag: {@link pl.lukmarr.thecastleofthemushroomderby.options.Config#TAG}
     */
    @GET("/publicJSONFeed")
    rx.Observable<RouteBody> getRouteList(@Query("command") String command, @Query("a") String agencyTag);

    /**
     * command is "routeConfig", a is agency tag: {@link pl.lukmarr.thecastleofthemushroomderby.options.Config#TAG}
     */
    @GET("/publicJSONFeed")
    rx.Observable<RouteConfigBody> getRouteConfig(@Query("command") String command,
                                           @Query("a") String agencyTag, @Query("r") String routeTag);


}
