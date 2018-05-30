package xyz.eventstreamer.eventstreamer.data;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import xyz.eventstreamer.eventstreamer.commons.Constants;
import xyz.eventstreamer.eventstreamer.data.event.local.EventDao;
import xyz.eventstreamer.eventstreamer.data.post.local.PostDao;
import xyz.eventstreamer.eventstreamer.model.database.EventEntity;
import xyz.eventstreamer.eventstreamer.model.database.PostEntity;
import xyz.eventstreamer.eventstreamer.util.TimeUtil;

@Database(entities = {EventEntity.class, PostEntity.class}, version = Constants.DATABASE_VERSION, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = Constants.DATABASE_NAME;
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context){
        if(instance == null){
            synchronized (LOCK) {
                Log.d(TAG, "Creating new database instance");
                instance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(TAG, "Getting database instance");
        return instance;
    }

    public abstract EventDao eventDao();

    public abstract PostDao postDao();

}
