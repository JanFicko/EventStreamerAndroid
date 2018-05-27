package xyz.eventstreamer.eventstreamer.ui.register;

import xyz.eventstreamer.eventstreamer.model.User;
import xyz.eventstreamer.eventstreamer.ui.BasePresenter;
import xyz.eventstreamer.eventstreamer.ui.BaseView;

public interface RegisterContract {

    interface View extends BaseView<Presenter> {
        void onSuccessfulRegister();
        void showErrorMessage();
    }

    interface Presenter extends BasePresenter {
        void registerUser(User user);
    }

}
