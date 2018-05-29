package xyz.eventstreamer.eventstreamer.ui.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

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

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLocationPermission();
        openDashboard(Animation.NONE);
    }

    @Override
    public void onBackPressed() {

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
    public void openLogin(int animationType) {
        if(loginFragment == null){
            loginFragment = LoginFragment.newInstance();
        }
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
        } else {
            dashboardFragment.showListView();
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
    public void openMap(@Animation.AnimationType int animationType) {
        if(dashboardFragment == null){
            dashboardFragment = DashboardFragment.newInstance();
        } else {
            dashboardFragment.showMapView();
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


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.dashboard_location_persmission_title)
                        .setMessage(R.string.dashboard_location_persmission_text)
                        .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATION);
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                    }
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }
}
