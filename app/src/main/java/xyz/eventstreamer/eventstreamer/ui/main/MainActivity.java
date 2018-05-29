package xyz.eventstreamer.eventstreamer.ui.main;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import xyz.eventstreamer.eventstreamer.R;
import xyz.eventstreamer.eventstreamer.commons.Animation;
import xyz.eventstreamer.eventstreamer.data.Injection;
import xyz.eventstreamer.eventstreamer.ui.BaseActivity;
import xyz.eventstreamer.eventstreamer.ui.aboutevent.AboutEventContract;
import xyz.eventstreamer.eventstreamer.ui.aboutevent.AboutEventFragment;
import xyz.eventstreamer.eventstreamer.ui.aboutevent.AboutEventPresenter;
import xyz.eventstreamer.eventstreamer.ui.addevent.AddEventFragment;
import xyz.eventstreamer.eventstreamer.ui.addevent.AddEventPresenter;
import xyz.eventstreamer.eventstreamer.ui.dashboard.DashboardFragment;
import xyz.eventstreamer.eventstreamer.ui.dashboard.DashboardPresenter;
import xyz.eventstreamer.eventstreamer.ui.findevent.FindEventFragment;
import xyz.eventstreamer.eventstreamer.ui.findevent.FindEventPresenter;
import xyz.eventstreamer.eventstreamer.ui.login.LoginFragment;
import xyz.eventstreamer.eventstreamer.ui.login.LoginPresenter;
import xyz.eventstreamer.eventstreamer.ui.profile.ProfileFragment;
import xyz.eventstreamer.eventstreamer.ui.register.RegisterFragment;
import xyz.eventstreamer.eventstreamer.ui.register.RegisterPresenter;

public class MainActivity
        extends
            BaseActivity
        implements
            MainContract.View {

    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    private DashboardFragment dashboardFragment;
    private FindEventFragment findEventFragment;
    private AboutEventFragment aboutEventFragment;
    private AddEventFragment addEventFragment;
    private ProfileFragment profileFragment;

    private RegisterPresenter registerPresenter;
    private LoginPresenter loginPresenter;
    private DashboardPresenter dashboardPresenter;
    private FindEventPresenter findEventPresenter;
    private AboutEventPresenter aboutEventPresenter;
    private AddEventPresenter addEventPresenter;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openDashboard(Animation.NONE);
    }

    @Override
    public void openRegister(int animationType) {
        if(registerFragment == null){
            registerFragment = RegisterFragment.newInstance();
        }
        if(registerPresenter == null){
            registerPresenter = new RegisterPresenter(
                    registerFragment,
                    Injection.provideUserRepository(getApplicationContext()),
                    Injection.provideSchedulerProvider()
            );
        }
        moveToNextFragment(registerFragment, animationType);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void openLogin(int animationType) {
        loginFragment = LoginFragment.newInstance();
        if(loginPresenter == null){
            loginPresenter = new LoginPresenter(
                    loginFragment,
                    Injection.provideUserRepository(getApplicationContext()),
                    Injection.provideSchedulerProvider()
            );
        }
        moveToNextFragment(loginFragment, animationType);
    }

    @Override
    public void openProfile(int animationType) {
        profileFragment = ProfileFragment.newInstance();
        moveToNextFragment(profileFragment, animationType);
    }

    @Override
    public void openDashboard(@Animation.AnimationType int animationType) {
        if(dashboardFragment == null){
            dashboardFragment = DashboardFragment.newInstance();
        }
        if(dashboardPresenter == null){
            dashboardPresenter = new DashboardPresenter(
                    dashboardFragment,
                    Injection.provideEventRepository(getApplicationContext()),
                    Injection.provideSchedulerProvider()
            );
        }
        moveToNextFragment(dashboardFragment, animationType);
    }

    @Override
    public void openFindEvent(@Animation.AnimationType int animationType) {
        if(findEventFragment == null){
            findEventFragment = FindEventFragment.newInstance();
        }
        if(findEventPresenter == null){
            findEventPresenter = new FindEventPresenter(
                    findEventFragment,
                    Injection.provideEventRepository(getApplicationContext()),
                    Injection.provideSchedulerProvider()
            );
        }
        moveToNextFragment(findEventFragment, animationType);
    }

    @Override
    public void openAboutEvent(@Animation.AnimationType int animationType) {
        if(aboutEventFragment == null){
            aboutEventFragment = AboutEventFragment.newInstance();
        }
        if(aboutEventPresenter == null){
            aboutEventPresenter = new AboutEventPresenter(
                    aboutEventFragment,
                    Injection.providePostRepository(getApplicationContext()),
                    Injection.provideSchedulerProvider()
            );
        }
        moveToNextFragment(aboutEventFragment, animationType);
    }

    @Override
    public void openAddEvent(@Animation.AnimationType int animationType) {
        if(addEventFragment == null){
            addEventFragment = AddEventFragment.newInstance();
        }
        if(addEventPresenter == null){
            addEventPresenter = new AddEventPresenter(
                    addEventFragment,
                    Injection.provideEventRepository(getApplicationContext()),
                    Injection.provideSchedulerProvider()
            );
        }
        moveToNextFragment(addEventFragment, animationType);
    }
}
