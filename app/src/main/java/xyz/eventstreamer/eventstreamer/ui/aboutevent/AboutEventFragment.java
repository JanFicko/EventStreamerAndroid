package xyz.eventstreamer.eventstreamer.ui.aboutevent;

import android.content.Context;
import android.os.Bundle;

import java.util.List;

import xyz.eventstreamer.eventstreamer.R;
import xyz.eventstreamer.eventstreamer.model.Post;
import xyz.eventstreamer.eventstreamer.ui.BaseFragment;
import xyz.eventstreamer.eventstreamer.ui.main.MainActivity;

public class AboutEventFragment extends BaseFragment implements AboutEventContract.View {

    private MainActivity activity;
    private AboutEventContract.Presenter presenter;

    public static AboutEventFragment newInstance(String eventId) {
        Bundle args = new Bundle();
        AboutEventFragment fragment = new AboutEventFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_about_event;
    }

    @Override
    public void setPresenter(AboutEventContract.Presenter presenter) {
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
    public void showPosts(List<Post> postList) {
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
