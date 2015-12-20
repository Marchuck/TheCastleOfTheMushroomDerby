package pl.lukmarr.thecastleofthemushroomderby.connection;

import pl.lukmarr.thecastleofthemushroomderby.Hero;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Łukasz Marczak
 *
 * @since 20.12.15
 */
public interface StarWarsClient {
    String SWAPI_ENDPOINT = "http://swapi.co";

    String WIKI_ENDPOINT = "http://pl.starwars.wikia.com/wiki";

    @GET("/api/people/{id}/")
    rx.Observable<Hero> getCharacter(@Path("id") Integer id);

    @GET("/{name}")
    rx.Observable<Response> getCharacterImage(@Path("name") String name);


}
