package xyz.eventstreamer.eventstreamer.data.post;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import xyz.eventstreamer.eventstreamer.model.Post;

public interface PostService {

    @GET("/api/post/{id}")
    Flowable<List<Post>> getPosts(@Path("id") String eventId);

    @POST("/api/post/")
    Flowable<Post> addPost(@Body Post post);

    @POST("/api/post/")
    @Multipart
    Flowable<Post> sendImage(@Part("id_dogodek") RequestBody eventId,
                             @Part MultipartBody.Part imageFile);

}
