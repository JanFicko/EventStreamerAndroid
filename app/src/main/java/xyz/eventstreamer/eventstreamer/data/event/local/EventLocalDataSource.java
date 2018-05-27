package xyz.eventstreamer.eventstreamer.data.event.local;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import xyz.eventstreamer.eventstreamer.EventStreamer;
import xyz.eventstreamer.eventstreamer.data.RetrofitFactory;
import xyz.eventstreamer.eventstreamer.data.event.EventDataSource;
import xyz.eventstreamer.eventstreamer.model.Event;
import xyz.eventstreamer.eventstreamer.util.schedulers.BaseSchedulerProvider;

public class EventLocalDataSource implements EventDataSource{

    private static EventLocalDataSource INSTANCE;

    private EventLocalDataSource(Context context,
                                 BaseSchedulerProvider schedulerProvider) {
        // TODO
    }

    public static EventLocalDataSource getInstance(
            Context context,
            BaseSchedulerProvider schedulerProvider) {
        if (INSTANCE == null) {
            INSTANCE = new EventLocalDataSource(context, schedulerProvider);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Flowable<List<Event>> getEvents() {
        // TODO
        return null;
    }

    @Override
    public Flowable<Event> getEventById(@NonNull String eventId) {
        // TODO
        return null;
    }

    @Override
    public Flowable<List<Event>> getEventByQuery(@NonNull String query) {
        // TODO
        return null;
    }

    @Override
    public Flowable<Event> addEvent(Event event) {
        // TODO
        return null;
    }

    @Override
    public Flowable<Event> removeEvent(Event event) {
        // TODO
        return null;
    }

    @Override
    public Flowable<Event> updateEvent(Event event) {
        // TODO
        return null;
    }

    @Override
    public Flowable<Event> addHashtag(Event event) {
        // TODO
        return null;
    }

    @Override
    public Flowable<Event> addLocation(Event event) {
        // TODO
        return null;
    }

    @Override
    public Flowable<Event> addCategory(Event event) {
        // TODO
        return null;
    }
}
