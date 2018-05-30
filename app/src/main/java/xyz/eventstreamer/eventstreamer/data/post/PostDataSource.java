package xyz.eventstreamer.eventstreamer.data.post;

import java.util.List;

import io.reactivex.Flowable;
import xyz.eventstreamer.eventstreamer.model.Post;
import xyz.eventstreamer.eventstreamer.model.database.PostEntity;

public interface PostDataSource {

    Flowable<List<Post>> getPosts(String eventId);

    Flowable<Post> addPost(Post post);

    Flowable<List<PostEntity>> getLocalPosts(String eventId);

    void addLocalPost(List<PostEntity> listPosts);

}
