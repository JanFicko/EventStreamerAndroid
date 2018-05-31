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
import xyz.eventstreamer.eventstreamer.ui.dialog.ProgressDialog;

public abstract class BaseFragment extends Fragment {

    protected String TAG = "";
    protected Context context;
    protected BaseActivity baseActivity;
    private ProgressDialog dfProgress;

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
        dfProgress = new ProgressDialog();
        dfProgress.setTargetFragment(this, 0);
        ButterKnife.bind(this, view);
        setupAfterBind();
        return view;
    }

    @LayoutRes
    protected abstract int setLayoutResId();

    protected void setupAfterBind(){

    }

    public void setLoadingIndicator(boolean active) {
        if (active) {
            if (!dfProgress.isAdded()) {
                try {
                    dfProgress.show(getFragmentManager(), TAG);
                } catch (IllegalStateException e) {
                    //For fragments inside fragments
                    try {
                        dfProgress.show(getChildFragmentManager(), TAG);
                    } catch (IllegalStateException ignore) {

                    }
                }
            }
        } else {
            if (dfProgress.isAdded()) {
                try {
                    dfProgress.dismiss();
                } catch (IllegalStateException e) {
                    dfProgress.dismissAllowingStateLoss();
                }
            }
        }
    }

}