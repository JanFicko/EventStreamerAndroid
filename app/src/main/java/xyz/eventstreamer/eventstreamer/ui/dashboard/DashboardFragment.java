package xyz.eventstreamer.eventstreamer.ui.dashboard;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import xyz.eventstreamer.eventstreamer.EventStreamer;
import xyz.eventstreamer.eventstreamer.R;
import xyz.eventstreamer.eventstreamer.commons.Animation;
import xyz.eventstreamer.eventstreamer.commons.Keys;
import xyz.eventstreamer.eventstreamer.model.Event;
import xyz.eventstreamer.eventstreamer.model.Location;
import xyz.eventstreamer.eventstreamer.model.User;
import xyz.eventstreamer.eventstreamer.ui.BaseFragment;
import xyz.eventstreamer.eventstreamer.ui.main.MainActivity;
import xyz.eventstreamer.eventstreamer.util.SharedPreferenceUtil;

public class DashboardFragment
        extends
            BaseFragment
        implements
            DashboardContract.View,
            EventAdapter.OnEventClickListener,
            SwipeRefreshLayout.OnRefreshListener,
MarkerClickListener {

    private MainActivity activity;
    private SharedPreferenceUtil sharedPreferenceUtil = EventStreamer.getInstance().getSharedPreferenceUtil();
    private DashboardContract.Presenter presenter;
    private EventAdapter eventAdapter;
    private User user;

    @BindView(R.id.srl_events)
    SwipeRefreshLayout srlEvents;
    @BindView(R.id.rv_events)
    RecyclerView rvEvents;
    @BindView(R.id.mv_map)
    MapView mvMap;
    @BindView(R.id.iv_event_list)
    ImageView ivList;
    @BindView(R.id.iv_event_map)
    ImageView ivMap;
    @BindView(R.id.iv_event_add)
    ImageView ivAddEvent;
    @BindView(R.id.iv_event_profile)
    ImageView ivProfile;

    public static DashboardFragment newInstance() {
        Bundle args = new Bundle();
        DashboardFragment fragment = new DashboardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_dashboard;
    }

    @Override
    public void setPresenter(DashboardContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) baseActivity;
    }

    @Override
    protected void setupAfterBind() {
        super.setupAfterBind();
        mvMap.setMarkerClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();

        user = sharedPreferenceUtil.retrieveObject(Keys.KEY_USER, User.class);
        if(user == null) {
            ivAddEvent.setVisibility(View.GONE);
        } else if(user.getTip().equalsIgnoreCase("Novinar")) {
            ivAddEvent.setVisibility(View.VISIBLE);
        }

        srlEvents.setOnRefreshListener(this);

        rvEvents.setLayoutManager(new LinearLayoutManager(context));
        rvEvents.setHasFixedSize(true);
        eventAdapter = new EventAdapter(null, this);
        rvEvents.setAdapter(eventAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void showEventsView(List<Event> eventList) {
        eventAdapter.updateList(eventList);

        for(Event event : eventList){
            if(event.getLokacija() != null && event.getLokacija().size() != 0) {
                Location location = event.getLokacija().get(0);
                mvMap.addMarker(event, location);
            }
        }
    }

    @Override
    public void showListView() {
        rvEvents.setVisibility(View.VISIBLE);
        mvMap.setVisibility(View.GONE);
    }

    @Override
    public void showMapView() {
        rvEvents.setVisibility(View.GONE);
        mvMap.setVisibility(View.VISIBLE);
    }

    @Override
    public void onEventClicked(Event event) {
        activity.openAboutEvent(Animation.LEFT, event.getIdDogodek());
    }

    @Override
    public void onMarkerClick(Event event) {
        activity.openAboutEvent(Animation.LEFT, event.getIdDogodek());
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        super.setLoadingIndicator(active);
        if(active){
            srlEvents.setRefreshing(true);
        } else {
            srlEvents.setRefreshing(false);
        }
    }

    @Override
    public void showErrorMessage() {
        // TODO
    }

    @Override
    public void showNoInternet() {
        // TODO
    }

    @Override
    public void onRefresh() {
        presenter.getEvents();
    }

    @OnClick({R.id.iv_event_list, R.id.iv_event_map, R.id.iv_event_add, R.id.iv_event_profile})
    public void onClickTab(View view){
        switch(view.getId()) {
            case R.id.iv_event_list:
                activity.openDashboard(Animation.RIGHT);
                break;
            case R.id.iv_event_map:
                activity.openMap(Animation.LEFT);
                break;
            case R.id.iv_event_add:
                activity.openAddEvent(Animation.LEFT);
                break;
            case R.id.iv_event_profile:
                if(user == null){
                    activity.openLogin(Animation.LEFT);
                } else {
                    activity.openProfile(Animation.LEFT);
                }
                break;
        }
    }
}
