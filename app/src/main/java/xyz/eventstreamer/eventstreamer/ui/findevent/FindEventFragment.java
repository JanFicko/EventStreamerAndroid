package xyz.eventstreamer.eventstreamer.ui.findevent;

import android.content.Context;
import android.os.Bundle;

import java.util.List;

import xyz.eventstreamer.eventstreamer.R;
import xyz.eventstreamer.eventstreamer.model.Event;
import xyz.eventstreamer.eventstreamer.ui.BaseFragment;
import xyz.eventstreamer.eventstreamer.ui.main.MainActivity;

public class FindEventFragment extends BaseFragment implements FindEventContract.View {

    private MainActivity activity;
    private FindEventContract.Presenter presenter;

    public static FindEventFragment newInstance() {
        Bundle args = new Bundle();
        FindEventFragment fragment = new FindEventFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_find_event;
    }

    @Override
    public void setPresenter(FindEventContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) baseActivity;
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
        // TODO
    }

    @Override
    public void showErrorMessage() {
        // TODO
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        // TODO
    }

    @Override
    public void showNoInternet() {
        // TODO
    }
}
