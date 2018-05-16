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
    public Flowable<List<Event>> getCards() {
        // TODO
        return null;
    }

    @Override
    public Flowable<Event> getEvent(@NonNull String eventId) {
        // TODO
        return null;
    }

    @Override
    public void addEvent(Event event) {
        // TODO
    }

    @Override
    public void removeCard(Event event) {
        // TODO
    }

}
