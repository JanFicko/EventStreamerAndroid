package xyz.eventstreamer.eventstreamer.data.event;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import xyz.eventstreamer.eventstreamer.model.Event;

public interface EventService {

    @GET("/event/")
    Flowable<List<Event>> getEvents();

}
