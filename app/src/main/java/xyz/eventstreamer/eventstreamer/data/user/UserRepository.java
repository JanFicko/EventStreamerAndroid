package xyz.eventstreamer.eventstreamer.data.user;

import io.reactivex.Flowable;
import xyz.eventstreamer.eventstreamer.EventStreamer;
import xyz.eventstreamer.eventstreamer.data.RetrofitFactory;
import xyz.eventstreamer.eventstreamer.model.BaseResponse;
import xyz.eventstreamer.eventstreamer.model.User;

public class UserRepository implements UserDataSource {

    private static UserRepository INSTANCE = null;

    private RetrofitFactory retrofitFactory = EventStreamer.getInstance().getRetrofit();

    private UserRepository() {}

    public static UserRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Flowable<User> login(User user) {
        UserService userService = retrofitFactory.userService();
        return userService.login(user);
    }

    @Override
    public Flowable<User> register(User user) {
        UserService userService = retrofitFactory.userService();
        return userService.registerUser(user);
    }
}
