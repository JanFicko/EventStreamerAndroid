package xyz.eventstreamer.eventstreamer.ui.aboutevent;

import java.util.List;

import xyz.eventstreamer.eventstreamer.model.Event;
import xyz.eventstreamer.eventstreamer.model.Post;
import xyz.eventstreamer.eventstreamer.ui.BasePresenter;
import xyz.eventstreamer.eventstreamer.ui.BaseView;
import xyz.eventstreamer.eventstreamer.ui.login.LoginContract;

public interface AboutEventContract {

    interface View extends BaseView<Presenter> {
        void showPosts(List<Post> postList);
        void showErrorMessage();
    }

    interface Presenter extends BasePresenter {
        void getPosts(Event event);
    }

}
