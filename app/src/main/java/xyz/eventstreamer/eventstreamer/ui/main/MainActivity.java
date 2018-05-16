package xyz.eventstreamer.eventstreamer.ui.main;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import xyz.eventstreamer.eventstreamer.R;
import xyz.eventstreamer.eventstreamer.commons.Animation;
import xyz.eventstreamer.eventstreamer.data.Injection;
import xyz.eventstreamer.eventstreamer.ui.BaseActivity;
import xyz.eventstreamer.eventstreamer.ui.aboutevent.AboutEventFragment;
import xyz.eventstreamer.eventstreamer.ui.addevent.AddEventFragment;
import xyz.eventstreamer.eventstreamer.ui.dashboard.DashboardFragment;
import xyz.eventstreamer.eventstreamer.ui.dashboard.DashboardPresenter;
import xyz.eventstreamer.eventstreamer.ui.findevent.FindEventFragment;

public class MainActivity
        extends
            BaseActivity
        implements
            MainContract.View {

    private DashboardFragment dashboardFragment;
    private FindEventFragment findEventFragment;
    private AboutEventFragment aboutEventFragment;
    private AddEventFragment addEventFragment;

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
    public void openDashboard(@Animation.AnimationType int animationType) {
        if(dashboardFragment == null){
            dashboardFragment = DashboardFragment.newInstance();
            new DashboardPresenter(
                    dashboardFragment,
                    Injection.provideEvemtRepository(getApplicationContext()),
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
        moveToNextFragment(findEventFragment, animationType);
    }

    @Override
    public void openAboutEvent(@Animation.AnimationType int animationType) {
        if(aboutEventFragment == null){
            aboutEventFragment = AboutEventFragment.newInstance();
        }
        moveToNextFragment(aboutEventFragment, animationType);
    }

    @Override
    public void openAddEvent(@Animation.AnimationType int animationType) {
        if(addEventFragment == null){
            addEventFragment = AddEventFragment.newInstance();
        }
        moveToNextFragment(addEventFragment, animationType);
    }
}
