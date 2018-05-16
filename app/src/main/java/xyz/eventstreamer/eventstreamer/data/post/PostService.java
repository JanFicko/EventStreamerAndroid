package xyz.eventstreamer.eventstreamer.data.post;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import xyz.eventstreamer.eventstreamer.model.Post;

public interface PostService {

    @GET("/post/{id}")
    Flowable<List<Post>> getPosts(@Path("id") String eventId);

}
