package xyz.eventstreamer.eventstreamer.data.post;

import java.util.List;

import io.reactivex.Flowable;
import xyz.eventstreamer.eventstreamer.model.Post;
import xyz.eventstreamer.eventstreamer.model.database.PostEntity;

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
        return postRemoteDataSource.getPosts(eventId);
    }

    @Override
    public Flowable<Post> addPost(Post post) {
        return postRemoteDataSource.addPost(post);
    }

    @Override
    public Flowable<List<PostEntity>> getLocalPosts(String eventId) {
        return postLocalDataSource.getLocalPosts(eventId);
    }

}
