package xyz.eventstreamer.eventstreamer.ui.login;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import xyz.eventstreamer.eventstreamer.EventStreamer;
import xyz.eventstreamer.eventstreamer.R;
import xyz.eventstreamer.eventstreamer.commons.Animation;
import xyz.eventstreamer.eventstreamer.commons.Keys;
import xyz.eventstreamer.eventstreamer.model.User;
import xyz.eventstreamer.eventstreamer.ui.BaseFragment;
import xyz.eventstreamer.eventstreamer.ui.main.MainActivity;
import xyz.eventstreamer.eventstreamer.util.SharedPreferenceUtil;
import xyz.eventstreamer.eventstreamer.util.ToastUtil;

public class LoginFragment extends BaseFragment implements LoginContract.View {

    private MainActivity activity;
    private LoginContract.Presenter presenter;
    private SharedPreferenceUtil sharedPreferenceUtil = EventStreamer.getInstance().getSharedPreferenceUtil();

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.til_email)
    TextInputLayout tilEmail;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_register)
    TextView tvRegister;

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
        tvToolbarTitle.setText(R.string.login);
        String registerText = getString(R.string.login_register_text);
        SpannableString spanString = new SpannableString(registerText);
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        tvRegister.setText(spanString);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void onSuccessfulLogin(User user) {
        sharedPreferenceUtil.saveObject(Keys.KEY_USER, user);
        activity.openDashboard(Animation.RIGHT);
    }

    @Override
    public void showErrorMessage() {
        ToastUtil.toastLong(context, R.string.error_login_unsuccessful);
    }

    @Override
    public void showNoInternet() {
        ToastUtil.toastLong(context, R.string.error_no_internet);
    }

    @OnClick(R.id.bt_login)
    public void onClickLogin(){
        boolean checker = true;
        if(etEmail.getText().toString().isEmpty()){
            tilEmail.setError(getString(R.string.empty_field));
            checker = false;
        } else if(etPassword.getText().toString().isEmpty()){
            tilPassword.setError(getString(R.string.empty_field));
            checker = false;
        }
        if(checker){
            User user = new User();
            user.setEmail(etEmail.getText().toString());
            user.setGeslo(etPassword.getText().toString());
            presenter.loginUser(user);
        }
    }

    @OnClick(R.id.tv_register)
    public void onClickRegister(){
        activity.openRegister(Animation.LEFT);
    }

    @OnClick(R.id.iv_back)
    public void onClickBack(){
        activity.openDashboard(Animation.RIGHT);
    }

}
