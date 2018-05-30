package xyz.eventstreamer.eventstreamer.data.event.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import xyz.eventstreamer.eventstreamer.model.database.EventEntity;


@Dao
public interface EventDao {

    @Query("SELECT * FROM event")
    Flowable<List<EventEntity>> loadAllEvents();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] bulkInsertEvents(List<EventEntity> listEvents);

}
