package pl.lukmarr.thecastleofthemushroomderby.connection;

import pl.lukmarr.thecastleofthemushroomderby.model.nextBus.agency.AgencyBody;
import pl.lukmarr.thecastleofthemushroomderby.model.nextBus.busLocation.BusLocationBody;
import pl.lukmarr.thecastleofthemushroomderby.model.nextBus.route.RouteBody;
import pl.lukmarr.thecastleofthemushroomderby.model.nextBus.config.RouteConfigBody;
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


    /**
     * real-time vehicle locations
     * command: "vehicleLocations"
     *
     * @param command
     * @return
     */
    @GET("/publicJSONFeed")
    rx.Observable<BusLocationBody> getBusLocation(@Query("command") String command, @Query("a") String agencyTag,
                                                  @Query("r") String routeTag, @Query("t") long timestamp);

//http://webservices.nextbus.com/service/publicJSONFeed?command=messages&a=<a gency tag>&r=<route tag1>&r=<route tagN>

    /**
     *
     * @param command messages
     * @param agencyTag
     * @param routeTag
     * @return
     */
    @GET("/publicJSONFeed")
    rx.Observable<BusLocationBody> getMessage(@Query("command") String command, @Query("a") String agencyTag,
                                              @Query("r") String routeTag);

    @GET("/publicJSONFeed")
    rx.Observable<BusLocationBody> getMessages(@Query("command") String command, @Query("a") String agencyTag,
                                               @Query("r") String routeTag, @Query("r") String anotherTag);


}
