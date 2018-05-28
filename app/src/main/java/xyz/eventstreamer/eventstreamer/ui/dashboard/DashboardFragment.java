package xyz.eventstreamer.eventstreamer.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import xyz.eventstreamer.eventstreamer.EventStreamer;
import xyz.eventstreamer.eventstreamer.R;
import xyz.eventstreamer.eventstreamer.commons.Animation;
import xyz.eventstreamer.eventstreamer.commons.Keys;
import xyz.eventstreamer.eventstreamer.model.Event;
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
            SwipeRefreshLayout.OnRefreshListener{

    private MainActivity activity;
    private SharedPreferenceUtil sharedPreferenceUtil = EventStreamer.getInstance().getSharedPreferenceUtil();
    private DashboardContract.Presenter presenter;

    private EventAdapter eventAdapter;

    @BindView(R.id.srl_events)
    SwipeRefreshLayout srlEvents;
    @BindView(R.id.rv_events)
    RecyclerView rvEvents;
    @BindView(R.id.iv_event_list)
    ImageView ivList;
    @BindView(R.id.iv_event_map)
    ImageView ivMap;
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
    public void onResume() {
        super.onResume();
        presenter.subscribe();

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
    }

    @Override
    public void onEventClicked(Event event) {
        // TODO
    }

    @Override
    public void setLoadingIndicator(boolean active) {
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

    @OnClick(R.id.iv_event_list)
    public void onClickList(){
        presenter.getEvents();
    }

    @OnClick(R.id.iv_event_map)
    public void onClickMap(){

    }

    @OnClick(R.id.iv_event_profile)
    public void onClickProfile(){
        User user = sharedPreferenceUtil.retrieveObject(Keys.KEY_USER, User.class);
        if(user == null){
            activity.openLogin(Animation.LEFT);
        } else {
            activity.openProfile(Animation.LEFT);
        }
    }

}
