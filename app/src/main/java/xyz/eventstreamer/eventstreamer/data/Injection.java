package xyz.eventstreamer.eventstreamer.data;

import android.content.Context;

import xyz.eventstreamer.eventstreamer.data.event.EventRepository;
import xyz.eventstreamer.eventstreamer.data.event.local.EventLocalDataSource;
import xyz.eventstreamer.eventstreamer.data.event.remote.EventRemoteDataSource;
import xyz.eventstreamer.eventstreamer.data.post.PostRepository;
import xyz.eventstreamer.eventstreamer.data.post.local.PostLocalDataSource;
import xyz.eventstreamer.eventstreamer.data.post.remote.PostRemoteDataSource;
import xyz.eventstreamer.eventstreamer.data.user.UserRepository;
import xyz.eventstreamer.eventstreamer.util.schedulers.BaseSchedulerProvider;
import xyz.eventstreamer.eventstreamer.util.schedulers.SchedulerProvider;

public class Injection {

    public static BaseSchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }

    public static EventRepository provideEventRepository(Context context) {
        return EventRepository.getInstance(
                EventRemoteDataSource.getInstance(),
                EventLocalDataSource.getInstance(context, provideSchedulerProvider()));
    }

    public static PostRepository providePostRepository(Context context) {
        return PostRepository.getInstance(
                PostRemoteDataSource.getInstance(),
                PostLocalDataSource.getInstance(context, provideSchedulerProvider()));
    }

    public static UserRepository provideUserRepository(Context context) {
        return UserRepository.getInstance();
    }

}
