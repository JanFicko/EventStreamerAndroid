package xyz.eventstreamer.eventstreamer.data.user;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import xyz.eventstreamer.eventstreamer.model.User;

public interface UserService {

    @GET("/login/")
    Flowable<User> login(@Body User user);

    @GET("/register/")
    Flowable<User> register(@Body User user);
}
