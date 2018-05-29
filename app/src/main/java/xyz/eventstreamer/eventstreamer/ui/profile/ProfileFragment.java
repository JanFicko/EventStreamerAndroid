package xyz.eventstreamer.eventstreamer.ui.profile;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.Executor;

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

public class ProfileFragment extends BaseFragment  {

    private MainActivity activity;
    private SharedPreferenceUtil sharedPreferenceUtil = EventStreamer.getInstance().getSharedPreferenceUtil();
    private GoogleSignInClient mGoogleSignInClient;

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_name_value)
    TextView tvName;
    @BindView(R.id.tv_surname_value)
    TextView tvSurname;
    @BindView(R.id.tv_email_value)
    TextView tvEmail;
    @BindView(R.id.tv_type_value)
    TextView tvType;
    @BindView(R.id.tv_medium_value)
    TextView tvMedium;

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_profile;
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
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
    }

    @Override
    public void onResume() {
        super.onResume();

        tvToolbarTitle.setText(R.string.profile);

        User user = sharedPreferenceUtil.retrieveObject(Keys.KEY_USER, User.class);
        tvName.setText(user.getIme());
        tvSurname.setText(user.getPriimek());
        tvEmail.setText(user.getEmail());
        tvType.setText(user.getTip());
        if(user.getTip().equalsIgnoreCase("Novinar")){
            tvMedium.setText(user.getMedij());
        } else {
            String noData = getString(R.string.no_data);
            SpannableString spanString = new SpannableString(noData);
            //spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
            spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
            tvMedium.setText(spanString);
        }
    }

    @OnClick(R.id.bt_logout)
    public void onBackLogout(){
        sharedPreferenceUtil.saveObject(Keys.KEY_USER, null);

        try {
            mGoogleSignInClient.signOut();
        } catch (Exception ignore){ }

        activity.openDashboard(Animation.RIGHT);
    }

    @OnClick(R.id.iv_back)
    public void onBackClick(){
        activity.openDashboard(Animation.LEFT);
    }
}
