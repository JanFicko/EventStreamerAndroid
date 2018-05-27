package xyz.eventstreamer.eventstreamer.ui.login;

import xyz.eventstreamer.eventstreamer.model.User;
import xyz.eventstreamer.eventstreamer.ui.BasePresenter;
import xyz.eventstreamer.eventstreamer.ui.BaseView;

public interface LoginContract {

    interface View extends BaseView<Presenter> {
        void onSuccessfulLogin(User user);
        void showErrorMessage();
    }

    interface Presenter extends BasePresenter {
        void loginUser(User user);
    }

}
