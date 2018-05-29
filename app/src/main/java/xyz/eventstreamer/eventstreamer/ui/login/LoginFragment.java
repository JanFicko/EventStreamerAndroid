package xyz.eventstreamer.eventstreamer.ui.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

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

import static xyz.eventstreamer.eventstreamer.commons.Constants.GOOGLE_SIGN_IN;

public class LoginFragment extends BaseFragment implements LoginContract.View {

    private MainActivity activity;
    private LoginContract.Presenter presenter;
    private SharedPreferenceUtil sharedPreferenceUtil = EventStreamer.getInstance().getSharedPreferenceUtil();

    private GoogleSignInClient mGoogleSignInClient;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .requestId()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
    }

    @Override
    public void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(activity);
        if(account != null){
            Log.d(TAG,"GOOGLE -> " + account.getEmail());
        } else {
            Log.d(TAG,"GOOGLE -> ni vpisan");
        }
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

    @OnClick(R.id.sign_in_button)
    public void onClickSignInGoogle(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            User user = new User();
            user.setEmail(account.getEmail());
            user.setGeslo(account.getId());
            user.setIme(account.getGivenName());
            user.setPriimek(account.getFamilyName());
            user.setTip("Uporabnik");
            presenter.loginUser(user);
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    @OnClick(R.id.bt_login)
    public void onClickLogin(){
        boolean checker = true;
        if(etEmail.getText().toString().isEmpty()){
            tilEmail.setError(getString(R.string.empty_field));
            checker = false;
        }
        if(etPassword.getText().toString().isEmpty()){
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


    @Override
    public void showErrorMessage() {
        try {
            mGoogleSignInClient.signOut();
        } catch (Exception ignore){ }

        ToastUtil.toastLong(context, R.string.error_login_unsuccessful);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        // TODO
    }

    @Override
    public void showNoInternet() {
        ToastUtil.toastLong(context, R.string.error_no_internet);
    }
}
