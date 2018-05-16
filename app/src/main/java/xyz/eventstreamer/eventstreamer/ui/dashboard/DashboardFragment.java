package xyz.eventstreamer.eventstreamer.ui.dashboard;

import android.os.Bundle;

import java.util.List;

import xyz.eventstreamer.eventstreamer.R;
import xyz.eventstreamer.eventstreamer.model.Event;
import xyz.eventstreamer.eventstreamer.ui.BaseFragment;

public class DashboardFragment
        extends
            BaseFragment
        implements
            DashboardContract.View {

    private DashboardContract.Presenter presenter;

    public static DashboardFragment newInstance() {
        Bundle args = new Bundle();
        DashboardFragment fragment = new DashboardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_dashboard;
    }

    @Override
    public void setPresenter(DashboardContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void showEventsView(List<Event> eventList) {

    }

    @Override
    public void setLoadingIndicator(boolean active) {
        // TODO
    }

    @Override
    public void showErrorMessage() {
        // TODO
    }

    @Override
    public void showNoInternet() {
        // TODO
    }
}
