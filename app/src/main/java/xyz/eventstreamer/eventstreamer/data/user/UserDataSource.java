package xyz.eventstreamer.eventstreamer.data.user;

import io.reactivex.Flowable;
import xyz.eventstreamer.eventstreamer.model.BaseResponse;
import xyz.eventstreamer.eventstreamer.model.User;

public interface UserDataSource {

    Flowable<User> login(User user);

    Flowable<User> register(User user);

}
