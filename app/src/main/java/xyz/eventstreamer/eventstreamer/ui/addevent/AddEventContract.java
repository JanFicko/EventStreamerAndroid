package xyz.eventstreamer.eventstreamer.ui.addevent;

import java.util.List;

import xyz.eventstreamer.eventstreamer.model.Event;
import xyz.eventstreamer.eventstreamer.ui.BasePresenter;
import xyz.eventstreamer.eventstreamer.ui.BaseView;
import xyz.eventstreamer.eventstreamer.ui.login.LoginContract;

public interface AddEventContract {

    interface View extends BaseView<Presenter> {
        void onSuccessfulEventAdded();
        void showErrorMessage();
    }

    interface Presenter extends BasePresenter {
        void addEvent(Event event);
    }

}