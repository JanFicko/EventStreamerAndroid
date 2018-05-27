package xyz.eventstreamer.eventstreamer.data.event;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import xyz.eventstreamer.eventstreamer.model.Event;

public class EventRepository implements EventDataSource {

    private static EventRepository INSTANCE = null;
    private final EventDataSource eventRemoteDataSource;
    private final EventDataSource eventLocalDataSource;

    private EventRepository(EventDataSource eventRemoteDataSource,
                          EventDataSource eventLocalDataSource) {
        this.eventRemoteDataSource = eventRemoteDataSource;
        this.eventLocalDataSource = eventLocalDataSource;
    }

    public static EventRepository getInstance(EventDataSource eventRemoteDataSource,
                                              EventDataSource eventLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new EventRepository(eventRemoteDataSource, eventLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Flowable<List<Event>> getEvents() {
        return eventRemoteDataSource.getEvents();
    }

    @Override
    public Flowable<List<Event>> getEventByQuery(@NonNull String query) {
        return eventRemoteDataSource.getEventByQuery(query);
    }

    @Override
    public Flowable<Event> getEventById(@NonNull String eventId) {
        return eventRemoteDataSource.getEventById(eventId);
    }

    @Override
    public Flowable<Event> addEvent(Event event) {
        return eventRemoteDataSource.addEvent(event);
    }

    @Override
    public Flowable<Event> removeEvent(Event event) {
        return eventRemoteDataSource.removeEvent(event);
    }

    @Override
    public Flowable<Event> updateEvent(Event event) {
        return eventRemoteDataSource.updateEvent(event);
    }

    @Override
    public Flowable<Event> addHashtag(Event event) {
        return eventRemoteDataSource.addHashtag(event);
    }

    @Override
    public Flowable<Event> addLocation(Event event) {
        return eventRemoteDataSource.addLocation(event);
    }

    @Override
    public Flowable<Event> addCategory(Event event) {
        return eventRemoteDataSource.addCategory(event);
    }

}
