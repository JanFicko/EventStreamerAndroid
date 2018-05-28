package xyz.eventstreamer.eventstreamer.ui.register;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import xyz.eventstreamer.eventstreamer.R;
import xyz.eventstreamer.eventstreamer.commons.Animation;
import xyz.eventstreamer.eventstreamer.model.User;
import xyz.eventstreamer.eventstreamer.ui.BaseFragment;
import xyz.eventstreamer.eventstreamer.ui.main.MainActivity;
import xyz.eventstreamer.eventstreamer.util.ToastUtil;

public class RegisterFragment extends BaseFragment implements RegisterContract.View {

    private MainActivity activity;
    private RegisterContract.Presenter presenter;

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.til_name)
    TextInputLayout tilName;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.til_surname)
    TextInputLayout tilSurname;
    @BindView(R.id.et_surname)
    EditText etSurname;
    @BindView(R.id.til_email)
    TextInputLayout tilEmail;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.cb_journalist)
    CheckBox cbJournalist;
    @BindView(R.id.til_medium)
    TextInputLayout tilMedium;
    @BindView(R.id.et_medium)
    EditText etMedium;

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
        tvToolbarTitle.setText(R.string.registration);

        cbJournalist.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                tilMedium.setVisibility(View.VISIBLE);
            } else {
                tilMedium.setVisibility(View.INVISIBLE);
            }
        });
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
        ToastUtil.toastLong(context, R.string.error_register_unsuccessful);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        // TODO
    }

    @Override
    public void showNoInternet() {
        ToastUtil.toastLong(context, R.string.error_no_internet);
    }

    @OnClick(R.id.bt_register)
    public void onClickRegister(){
        boolean checker = true;
        if(etName.getText().toString().isEmpty()){
            tilName.setError(getString(R.string.empty_field));
            checker = false;
        }
        if(etSurname.getText().toString().isEmpty()){
            tilSurname.setError(getString(R.string.empty_field));
            checker = false;
        }
        if(etEmail.getText().toString().isEmpty()){
            tilEmail.setError(getString(R.string.empty_field));
            checker = false;
        }
        if(etPassword.getText().toString().isEmpty()){
            tilPassword.setError(getString(R.string.empty_field));
            checker = false;
        }
        if(cbJournalist.isChecked() && etMedium.getText().toString().isEmpty()){
            tilMedium.setError(getString(R.string.empty_field));
            checker = false;
        }
        if(checker){
            User user = new User();
            user.setIme(etName.getText().toString());
            user.setPriimek(etSurname.getText().toString());
            user.setEmail(etEmail.getText().toString());
            user.setGeslo(etPassword.getText().toString());
            if(cbJournalist.isChecked()){
                user.setTip("Novinar");
                user.setMedij(etMedium.getText().toString());
            } else {
                user.setTip("Uporabnik");
            }
            presenter.registerUser(user);
        }
    }

    @OnClick(R.id.iv_back)
    public void onClickBack(){
        activity.openLogin(Animation.RIGHT);
    }

}
