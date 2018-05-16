package xyz.eventstreamer.eventstreamer.data.post;

import java.util.List;

import io.reactivex.Flowable;
import xyz.eventstreamer.eventstreamer.model.Post;

public interface PostDataSource {

    Flowable<List<Post>> getPosts(String eventId);

    void addPost(Post post);
}
