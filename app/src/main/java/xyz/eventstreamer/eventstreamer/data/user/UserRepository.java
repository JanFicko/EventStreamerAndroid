package xyz.eventstreamer.eventstreamer.data.user;

import io.reactivex.Flowable;
import xyz.eventstreamer.eventstreamer.model.User;

public class UserRepository implements UserDataSource {

    private static UserRepository INSTANCE = null;

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
        // TODO
        return null;
    }

    @Override
    public void register(User user) {
        // TODO
    }
}
