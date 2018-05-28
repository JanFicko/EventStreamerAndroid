package xyz.eventstreamer.eventstreamer.data.user;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import xyz.eventstreamer.eventstreamer.model.BaseResponse;
import xyz.eventstreamer.eventstreamer.model.User;

public interface UserService {

    @POST("/api/user/login")
    Flowable<User> login(@Body User user);

    @POST("/api/user/")
    Flowable<User> registerUser(@Body User user);

    @PUT("/api/user/")
    Flowable<User> updateUser(@Body User user);

    @GET("/api/user/")
    Flowable<List<User>> getUsers();

    @GET("/api/user/:id")
    Flowable<User> getUser(@Path("id") int id);

    @DELETE("/api/user/")
    Flowable<User> deleteUser(@Body User user);

    @POST("/api/user/kategorija")
    Flowable<User> categories(@Body User user);

}
