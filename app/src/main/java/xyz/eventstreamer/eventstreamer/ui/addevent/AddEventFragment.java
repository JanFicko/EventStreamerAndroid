package xyz.eventstreamer.eventstreamer.ui.addevent;

import android.content.Context;
import android.os.Bundle;

import xyz.eventstreamer.eventstreamer.R;
import xyz.eventstreamer.eventstreamer.commons.Animation;
import xyz.eventstreamer.eventstreamer.ui.BaseFragment;
import xyz.eventstreamer.eventstreamer.ui.login.LoginContract;
import xyz.eventstreamer.eventstreamer.ui.main.MainActivity;

public class AddEventFragment extends BaseFragment implements AddEventContract.View {

    private MainActivity activity;
    private AddEventContract.Presenter presenter;

    public static AddEventFragment newInstance() {
        Bundle args = new Bundle();
        AddEventFragment fragment = new AddEventFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_add_event;
    }


    @Override
    public void setPresenter(AddEventContract.Presenter presenter) {
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
    public void onSuccessfulEventAdded() {
        activity.openDashboard(Animation.RIGHT);
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
