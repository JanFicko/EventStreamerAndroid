package xyz.eventstreamer.eventstreamer.data.event;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import xyz.eventstreamer.eventstreamer.model.Event;

public interface EventDataSource {

    Flowable<List<Event>> getCards();

    Flowable<Event> getEvent(@NonNull String eventId);

    void addEvent(Event event);

    void removeCard(Event event);
}
