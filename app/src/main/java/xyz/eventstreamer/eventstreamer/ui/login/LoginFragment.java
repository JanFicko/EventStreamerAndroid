package xyz.eventstreamer.eventstreamer.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import xyz.eventstreamer.eventstreamer.R;
import xyz.eventstreamer.eventstreamer.commons.Animation;
import xyz.eventstreamer.eventstreamer.model.User;
import xyz.eventstreamer.eventstreamer.ui.BaseFragment;
import xyz.eventstreamer.eventstreamer.ui.main.MainActivity;

public class LoginFragment extends BaseFragment implements LoginContract.View {

    private MainActivity activity;
    private LoginContract.Presenter presenter;

    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_login;
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
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
    public void onSuccessfulLogin(User user) {
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
