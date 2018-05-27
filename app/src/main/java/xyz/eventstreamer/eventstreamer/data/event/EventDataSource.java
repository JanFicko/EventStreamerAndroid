package xyz.eventstreamer.eventstreamer.data.event;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import xyz.eventstreamer.eventstreamer.model.Event;

public interface EventDataSource {

    Flowable<List<Event>> getEvents();

    Flowable<Event> getEventById(@NonNull String eventId);

    Flowable<List<Event>> getEventByQuery(@NonNull String query);

    Flowable<Event> addEvent(Event event);

    Flowable<Event> removeEvent(Event event);

    Flowable<Event> updateEvent(Event event);

    Flowable<Event> addHashtag(Event event);

    Flowable<Event> addLocation(Event event);

    Flowable<Event> addCategory(Event event);
}
