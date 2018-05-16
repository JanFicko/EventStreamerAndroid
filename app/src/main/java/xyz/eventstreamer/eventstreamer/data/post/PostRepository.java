package xyz.eventstreamer.eventstreamer.data.post;

import java.util.List;

import io.reactivex.Flowable;
import xyz.eventstreamer.eventstreamer.model.Post;

public class PostRepository implements PostDataSource {

    private static PostRepository INSTANCE = null;
    private final PostDataSource postRemoteDataSource;
    private final PostDataSource postLocalDataSource;

    private PostRepository(PostDataSource postRemoteDataSource,
                           PostDataSource postLocalDataSource) {
        this.postRemoteDataSource = postRemoteDataSource;
        this.postLocalDataSource = postLocalDataSource;
    }

    public static PostRepository getInstance(PostDataSource postRemoteDataSource,
                                             PostDataSource postLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new PostRepository(postRemoteDataSource, postLocalDataSource);
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
    public void addPost(Post post) {
        // TODO
    }
}
