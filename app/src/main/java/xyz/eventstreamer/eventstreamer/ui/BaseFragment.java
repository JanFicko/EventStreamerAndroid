package xyz.eventstreamer.eventstreamer.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    protected String TAG = "";
    protected Context context;
    protected BaseActivity baseActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        TAG = getClass().getSimpleName();
        this.context = context;
        this.baseActivity = (BaseActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setLayoutResId(), container, false);
        ButterKnife.bind(this, view);
        setupAfterBind();
        return view;
    }

    @LayoutRes
    protected abstract int setLayoutResId();

    protected void setupAfterBind(){

    }

}