package xyz.eventstreamer.eventstreamer.ui.addevent;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import xyz.eventstreamer.eventstreamer.EventStreamer;
import xyz.eventstreamer.eventstreamer.R;
import xyz.eventstreamer.eventstreamer.commons.Animation;
import xyz.eventstreamer.eventstreamer.commons.Keys;
import xyz.eventstreamer.eventstreamer.model.Category;
import xyz.eventstreamer.eventstreamer.model.Event;
import xyz.eventstreamer.eventstreamer.model.Location;
import xyz.eventstreamer.eventstreamer.model.User;
import xyz.eventstreamer.eventstreamer.ui.BaseFragment;
import xyz.eventstreamer.eventstreamer.ui.dialog.CategoryPickerDialogFragment;
import xyz.eventstreamer.eventstreamer.ui.dialog.DatePickerDialogFragment;
import xyz.eventstreamer.eventstreamer.ui.dialog.OnCategoryListener;
import xyz.eventstreamer.eventstreamer.ui.dialog.OnDateTimeListener;
import xyz.eventstreamer.eventstreamer.ui.dialog.TimePickerDialogFragment;
import xyz.eventstreamer.eventstreamer.ui.login.LoginContract;
import xyz.eventstreamer.eventstreamer.ui.main.MainActivity;
import xyz.eventstreamer.eventstreamer.util.SharedPreferenceUtil;
import xyz.eventstreamer.eventstreamer.util.TimeUtil;
import xyz.eventstreamer.eventstreamer.util.ToastUtil;

public class AddEventFragment
        extends
            BaseFragment
        implements
            AddEventContract.View,
            OnDateTimeListener,
            OnCategoryListener {

    private MainActivity activity;
    private AddEventContract.Presenter presenter;
    private SharedPreferenceUtil sharedPreferenceUtil = EventStreamer.getInstance().getSharedPreferenceUtil();
    private Calendar calendar;
    private Event event = new Event();

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.til_title)
    TextInputLayout tilTitle;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.til_description)
    TextInputLayout tilDescription;
    @BindView(R.id.et_description)
    EditText etDescription;
    @BindView(R.id.til_date)
    TextInputLayout tilDate;
    @BindView(R.id.et_date)
    EditText etDate;

    private static final int PLACE_PICKER_REQUEST = 1;;

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

        tvToolbarTitle.setText(R.string.add_event);

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

    @OnClick(R.id.bt_add_event)
    public void onClickAddEvent(){
        boolean checker = true;
        Log.d(TAG, event.toString());
        if(etTitle.getText().toString().isEmpty()){
            tilTitle.setError(getString(R.string.empty_field));
            checker = false;
        } else if(etDescription.getText().toString().isEmpty()){
            tilDescription.setError(getString(R.string.empty_field));
            checker = false;
        } else if(etDate.getText().toString().isEmpty()){
            tilDate.setError(getString(R.string.empty_field));
            checker = false;
        } else if(event.getKategorija() == null){
            ToastUtil.toastLong(context, R.string.error_no_category_picked);
            checker = false;
        }
        if(checker){
            User user = sharedPreferenceUtil.retrieveObject(Keys.KEY_USER, User.class);
            event.setIdUporabnik(user.getIdUporabnik());
            event.setNaziv(etTitle.getText().toString());
            event.setOpis(etDescription.getText().toString());
            presenter.addEvent(event);
        }
    }

    @OnClick(R.id.iv_back)
    public void onClickBack(){
        activity.openDashboard(Animation.RIGHT);
    }

    @OnClick(R.id.et_date)
    public void onClickDate(){
        DialogFragment dateFragment = new DatePickerDialogFragment(this);
        dateFragment.show(activity.getSupportFragmentManager(), "datePicker");
    }

    @OnClick(R.id.bt_category)
    public void onClickCategory(){
        DialogFragment categoryFragment = new CategoryPickerDialogFragment(this);
        categoryFragment.show(activity.getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PLACE_PICKER_REQUEST){
            if(resultCode == Activity.RESULT_OK){
                Place place = PlacePicker.getPlace(data, activity);
                LatLng latLng = place.getLatLng();

                List<Location> locationList = new ArrayList<>();
                Location location = new Location();
                location.setLongitude(latLng.longitude);
                location.setLatitude(latLng.latitude);
                locationList.add(location);

                event.setLokacija(locationList);
            }
        }
    }

    @OnClick(R.id.bt_location)
    public void onClickLocation(){
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(activity), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDateSet(int year, int month, int day) {
        calendar.set(year, month, day);

        DialogFragment timeFragment = new TimePickerDialogFragment(this);
        timeFragment.show(activity.getSupportFragmentManager(), "categoryPicker");
    }

    @Override
    public void onTimeSet(int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        etDate.setText(TimeUtil.generateCurrentTimeAndDateFromMillis(calendar.getTimeInMillis()));

        event.setDatum(String.valueOf(calendar.getTimeInMillis()));
    }

    @Override
    public void onCategoriesPicked(List<String> categoriesList) {
        List<Category> categoryList = new ArrayList<>();
        for(String categoryName : categoriesList) {
            Category category = new Category();
            category.setNaziv(categoryName);
            categoryList.add(category);
        }
        event.setKategorija(categoryList);
    }

    @Override
    public void showErrorMessage() {
        ToastUtil.toastLong(context, R.string.error_add_event_unsuccessful);
    }

    @Override
    public void showNoInternet() {
        ToastUtil.toastLong(context, R.string.error_no_internet);
    }
}
