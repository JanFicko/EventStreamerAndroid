package xyz.eventstreamer.eventstreamer.ui.addevent;

import android.os.Bundle;

import xyz.eventstreamer.eventstreamer.R;
import xyz.eventstreamer.eventstreamer.ui.BaseFragment;

public class AddEventFragment extends BaseFragment {

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
}
