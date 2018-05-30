package xyz.eventstreamer.eventstreamer.ui.dashboard;

import java.util.List;

import xyz.eventstreamer.eventstreamer.model.Event;
import xyz.eventstreamer.eventstreamer.ui.BasePresenter;
import xyz.eventstreamer.eventstreamer.ui.BaseView;

public interface DashboardContract {

    interface View extends BaseView<Presenter> {
        void showEventsView(List<Event> eventList);
        void showLocalEventsView(List<Event> eventList);
        void showListView();
        void showMapView();
        void showErrorMessage();
    }

    interface Presenter extends BasePresenter {
        void getEvents();
        void getLocalEvents();
    }

}
