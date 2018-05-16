package xyz.eventstreamer.eventstreamer.ui.findevent;

import android.os.Bundle;

import xyz.eventstreamer.eventstreamer.R;
import xyz.eventstreamer.eventstreamer.ui.BaseFragment;

public class FindEventFragment extends BaseFragment {

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

}
