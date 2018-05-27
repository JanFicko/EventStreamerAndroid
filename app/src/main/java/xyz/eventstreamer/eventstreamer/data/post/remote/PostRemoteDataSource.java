package xyz.eventstreamer.eventstreamer.data.post.remote;

import java.util.List;

import javax.sql.DataSource;

import io.reactivex.Flowable;
import xyz.eventstreamer.eventstreamer.EventStreamer;
import xyz.eventstreamer.eventstreamer.data.RetrofitFactory;
import xyz.eventstreamer.eventstreamer.data.post.PostDataSource;
import xyz.eventstreamer.eventstreamer.data.post.PostService;
import xyz.eventstreamer.eventstreamer.model.Post;

public class PostRemoteDataSource implements PostDataSource {

    private static PostRemoteDataSource INSTANCE;

    private RetrofitFactory retrofitFactory = EventStreamer.getInstance().getRetrofit();

    public static PostRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PostRemoteDataSource();;
        }
        return INSTANCE;
    }

    private PostRemoteDataSource() { }

    @Override
    public Flowable<List<Post>> getPosts(String eventId) {
        PostService postService = retrofitFactory.postService();
        return postService.getPosts(eventId);
    }

    @Override
    public Flowable<Post> addPost(Post post) {
        PostService postService = retrofitFactory.postService();
        return postService.addPost(post);
    }
}
