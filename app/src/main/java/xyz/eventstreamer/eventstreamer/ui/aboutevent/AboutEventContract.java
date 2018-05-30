package xyz.eventstreamer.eventstreamer.ui.aboutevent;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import xyz.eventstreamer.eventstreamer.model.Event;
import xyz.eventstreamer.eventstreamer.model.Post;
import xyz.eventstreamer.eventstreamer.ui.BasePresenter;
import xyz.eventstreamer.eventstreamer.ui.BaseView;
import xyz.eventstreamer.eventstreamer.ui.login.LoginContract;

public interface AboutEventContract {

    interface View extends BaseView<Presenter> {
        void showPosts(List<Post> postList);
        void showLocalPosts(List<Post> postList);
        void onPostSentSuccessfuly();
        void showErrorMessage();
    }

    interface Presenter extends BasePresenter {
        void getPosts(Event event);
        void sendPost(Post post);
        void sendImage(RequestBody eventId, MultipartBody.Part image);
        void getLocalPosts(String eventId);
    }

}
