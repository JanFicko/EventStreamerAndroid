package xyz.eventstreamer.eventstreamer.data.event.remote;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import xyz.eventstreamer.eventstreamer.EventStreamer;
import xyz.eventstreamer.eventstreamer.data.RetrofitFactory;
import xyz.eventstreamer.eventstreamer.data.event.EventDataSource;
import xyz.eventstreamer.eventstreamer.model.Event;

public class EventRemoteDataSource implements EventDataSource{

    private static EventRemoteDataSource INSTANCE;

    private RetrofitFactory retrofitFactory = EventStreamer.getInstance().getRetrofit();

    public static EventRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EventRemoteDataSource();;
        }
        return INSTANCE;
    }

    private EventRemoteDataSource() { }

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
