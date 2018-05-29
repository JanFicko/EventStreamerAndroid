package xyz.eventstreamer.eventstreamer.ui.addevent;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

import butterknife.OnClick;
import butterknife.OnFocusChange;
import xyz.eventstreamer.eventstreamer.R;
import xyz.eventstreamer.eventstreamer.commons.Animation;
import xyz.eventstreamer.eventstreamer.ui.BaseFragment;
import xyz.eventstreamer.eventstreamer.ui.dialog.DatePickerDialogFragment;
import xyz.eventstreamer.eventstreamer.ui.dialog.OnDateTimeListener;
import xyz.eventstreamer.eventstreamer.ui.dialog.TimePickerDialogFragment;
import xyz.eventstreamer.eventstreamer.ui.login.LoginContract;
import xyz.eventstreamer.eventstreamer.ui.main.MainActivity;

public class AddEventFragment
        extends
            BaseFragment
        implements
            AddEventContract.View,
            OnDateTimeListener {

    private MainActivity activity;
    private AddEventContract.Presenter presenter;

    private Calendar calendar;

    public static AddEventFragment newInstance() {
        Bundle args = new Bundle();
        AddEventFragment fragment = new AddEventFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_add_event;
    }


    @Override
    public void setPresenter(AddEventContract.Presenter presenter) {
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

        calendar = Calendar.getInstance();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void onSuccessfulEventAdded() {
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

    @OnClick(R.id.iv_back)
    public void onClickBack(){
        activity.openDashboard(Animation.RIGHT);
    }

    @OnFocusChange(R.id.et_date)
    public void onClickDate(){
        DialogFragment dateFragment = new DatePickerDialogFragment(this);

        dateFragment.setCancelable(false);
        dateFragment.show(activity.getSupportFragmentManager(), "datePicker");

    }

    @Override
    public void onDateSet(int year, int month, int day) {
        DialogFragment timeFragment = new TimePickerDialogFragment(this);
        timeFragment.setCancelable(false);
        timeFragment.show(activity.getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onTimeSet(int hourOfDay, int minute) {
        // TODO
    }
}
