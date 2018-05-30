package xyz.eventstreamer.eventstreamer.data.event.remote;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import xyz.eventstreamer.eventstreamer.EventStreamer;
import xyz.eventstreamer.eventstreamer.data.RetrofitFactory;
import xyz.eventstreamer.eventstreamer.data.event.EventDataSource;
import xyz.eventstreamer.eventstreamer.data.event.EventService;
import xyz.eventstreamer.eventstreamer.model.Event;
import xyz.eventstreamer.eventstreamer.model.database.EventEntity;

public class EventRemoteDataSource implements EventDataSource{

    private static EventRemoteDataSource INSTANCE;

    private RetrofitFactory retrofitFactory = EventStreamer.getInstance().getRetrofit();

    public static EventRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EventRemoteDataSource();
        }
        return INSTANCE;
    }

    private EventRemoteDataSource() { }

    @Override
    public Flowable<List<Event>> getEvents() {
        EventService eventService = retrofitFactory.eventService();
        return eventService.getEvents();
    }

    @Override
    public Flowable<Event> getEventById(@NonNull String eventId) {
        EventService eventService = retrofitFactory.eventService();
        return eventService.getEventById(eventId);
    }

    @Override
    public Flowable<List<Event>> getEventByQuery(@NonNull String query) {
        EventService eventService = retrofitFactory.eventService();
        return eventService.getEventsByQuery(query);
    }

    @Override
    public Flowable<Event> addEvent(Event event) {
        EventService eventService = retrofitFactory.eventService();
        return eventService.createEvent(event);
    }

    @Override
    public Flowable<Event> removeEvent(Event event) {
        EventService eventService = retrofitFactory.eventService();
        return eventService.removeEvent(event);
    }

    @Override
    public Flowable<Event> updateEvent(Event event) {
        EventService eventService = retrofitFactory.eventService();
        return eventService.updateEvent(event);
    }

    @Override
    public Flowable<Event> addHashtag(Event event) {
        EventService eventService = retrofitFactory.eventService();
        return eventService.addHashtag(event);
    }

    @Override
    public Flowable<Event> addLocation(Event event) {
        EventService eventService = retrofitFactory.eventService();
        return eventService.addLocation(event);
    }

    @Override
    public Flowable<Event> addCategory(Event event) {
        EventService eventService = retrofitFactory.eventService();
        return eventService.addCategory(event);
    }

    @Override
    public Flowable<List<EventEntity>> getLocalEvents() {
        return null;
    }


}
