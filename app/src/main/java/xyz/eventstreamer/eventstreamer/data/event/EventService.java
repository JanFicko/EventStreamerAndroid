package xyz.eventstreamer.eventstreamer.data.event;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import xyz.eventstreamer.eventstreamer.model.Event;

public interface EventService {

    @GET("/api/event/")
    Flowable<List<Event>> getEvents();

    @POST("/api/event/")
    Flowable<Event> createEvent(@Body Event event);

    @POST("/api/event/kategorija")
    Flowable<Event> addCategory(@Body Event event);

    @POST("/api/event/lokacija")
    Flowable<Event> addLocation(@Body Event event);

    @POST("/api/event/hashtag")
    Flowable<Event> addHashtag(@Body Event event);

    @PUT("/api/event/")
    Flowable<Event> updateEvent(@Body Event event);
    
    @DELETE("/api/event/")
    Flowable<Event> removeEvent(@Body Event event);

    @GET("/api/event/{id}")
    Flowable<Event> getEventById(@Path("id") String idEvent);

    @GET("/api/event/{query}")
    Flowable<List<Event>> getEventsByQuery(@Path("query") String query);

}
