package xyz.eventstreamer.eventstreamer.data.post.local;

import android.content.Context;

import java.util.List;

import io.reactivex.Flowable;
import xyz.eventstreamer.eventstreamer.data.post.PostDataSource;
import xyz.eventstreamer.eventstreamer.model.Post;
import xyz.eventstreamer.eventstreamer.util.schedulers.BaseSchedulerProvider;

public class PostLocalDataSource implements PostDataSource {

    private static PostLocalDataSource INSTANCE;

    private PostLocalDataSource(Context context,
                                 BaseSchedulerProvider schedulerProvider) {
        // TODO
    }

    public static PostLocalDataSource getInstance(
            Context context,
            BaseSchedulerProvider schedulerProvider) {
        if (INSTANCE == null) {
            INSTANCE = new PostLocalDataSource(context, schedulerProvider);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Flowable<List<Post>> getPosts(String eventId) {
        // TODO
        return null;
    }

    @Override
    public Flowable<Post> addPost(Post post) {
        // TODO
        return null;
    }
}
