package xyz.eventstreamer.eventstreamer.data.post.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import xyz.eventstreamer.eventstreamer.model.database.EventEntity;
import xyz.eventstreamer.eventstreamer.model.database.PostEntity;


@Dao
public interface PostDao {

    @Query("SELECT * FROM post WHERE id_dogodek = :eventId")
    Flowable<List<PostEntity>> loadAllPosts(String eventId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] bulkInsertPosts(List<PostEntity> listPosts);

}
