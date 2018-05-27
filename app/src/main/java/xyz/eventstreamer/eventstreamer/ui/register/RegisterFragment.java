package xyz.eventstreamer.eventstreamer.ui.register;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import xyz.eventstreamer.eventstreamer.R;
import xyz.eventstreamer.eventstreamer.commons.Animation;
import xyz.eventstreamer.eventstreamer.ui.BaseFragment;
import xyz.eventstreamer.eventstreamer.ui.main.MainActivity;

public class RegisterFragment extends BaseFragment implements RegisterContract.View {

    private MainActivity activity;
    private RegisterContract.Presenter presenter;

    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_password_repeat)
    EditText etPassworRepeat;

    public static RegisterFragment newInstance() {
        Bundle args = new Bundle();
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_register;
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
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
    public void onSuccessfulRegister() {
        activity.openLogin(Animation.RIGHT);
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
