package xyz.eventstreamer.eventstreamer.ui.aboutevent;

import android.os.Bundle;

import xyz.eventstreamer.eventstreamer.R;
import xyz.eventstreamer.eventstreamer.ui.BaseFragment;

public class AboutEventFragment extends BaseFragment{

    public static AboutEventFragment newInstance() {
        Bundle args = new Bundle();
        AboutEventFragment fragment = new AboutEventFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_about_event;
    }

}
