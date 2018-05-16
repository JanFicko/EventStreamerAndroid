package xyz.eventstreamer.eventstreamer.ui.dashboard;

import java.util.List;

import xyz.eventstreamer.eventstreamer.model.Event;
import xyz.eventstreamer.eventstreamer.ui.BasePresenter;
import xyz.eventstreamer.eventstreamer.ui.BaseView;

public interface DashboardContract {

    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);
        void showEventsView(List<Event> eventList);
        void showErrorMessage();
    }

    interface Presenter extends BasePresenter {
        void getEvents();
    }

}
