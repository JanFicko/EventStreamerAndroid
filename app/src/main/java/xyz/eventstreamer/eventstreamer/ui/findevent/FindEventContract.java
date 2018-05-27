package xyz.eventstreamer.eventstreamer.ui.findevent;

import java.util.List;

import xyz.eventstreamer.eventstreamer.model.Event;
import xyz.eventstreamer.eventstreamer.model.User;
import xyz.eventstreamer.eventstreamer.ui.BasePresenter;
import xyz.eventstreamer.eventstreamer.ui.BaseView;
import xyz.eventstreamer.eventstreamer.ui.login.LoginContract;

public interface FindEventContract {

    interface View extends BaseView<Presenter> {
        void showEventsView(List<Event> eventList);
        void showErrorMessage();
    }

    interface Presenter extends BasePresenter {
        void findEventsByQuery(String query);
    }

}
