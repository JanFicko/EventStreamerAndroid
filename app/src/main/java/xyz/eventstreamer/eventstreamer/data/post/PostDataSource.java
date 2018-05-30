package xyz.eventstreamer.eventstreamer.data.post;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import xyz.eventstreamer.eventstreamer.model.Post;
import xyz.eventstreamer.eventstreamer.model.database.PostEntity;

public interface PostDataSource {

    Flowable<List<Post>> getPosts(String eventId);

    Flowable<Post> addPost(Post post);

    Flowable<Post> addPost(RequestBody eventId, MultipartBody.Part image);

    Flowable<List<PostEntity>> getLocalPosts(String eventId);

}
