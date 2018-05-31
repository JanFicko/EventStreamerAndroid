package xyz.eventstreamer.eventstreamer.ui.findevent;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import xyz.eventstreamer.eventstreamer.R;
import xyz.eventstreamer.eventstreamer.commons.Animation;
import xyz.eventstreamer.eventstreamer.model.Event;
import xyz.eventstreamer.eventstreamer.ui.BaseFragment;
import xyz.eventstreamer.eventstreamer.ui.dashboard.EventAdapter;
import xyz.eventstreamer.eventstreamer.ui.main.MainActivity;
import xyz.eventstreamer.eventstreamer.util.ToastUtil;

public class FindEventFragment
        extends
            BaseFragment
        implements
            FindEventContract.View, EventAdapter.OnEventClickListener,
        TextWatcher {

    private MainActivity activity;
    private FindEventContract.Presenter presenter;
    private EventAdapter eventAdapter;

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rv_events)
    RecyclerView rvEvents;
    @BindView(R.id.rl_no_event)
    RelativeLayout rlNoEvents;
    @BindView(R.id.tv_placeholder)
    TextView tvPlaceholder;

    public static FindEventFragment newInstance() {
        Bundle args = new Bundle();
        FindEventFragment fragment = new FindEventFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_find_event;
    }

    @Override
    public void setPresenter(FindEventContract.Presenter presenter) {
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

        etSearch.addTextChangedListener(this);

        rlNoEvents.setVisibility(View.VISIBLE);
        rvEvents.setVisibility(View.GONE);

        tvToolbarTitle.setText(R.string.find_event);

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

    @OnClick(R.id.iv_back)
    public void onBackClick(){
        activity.openDashboard(Animation.RIGHT);
    }

    @Override
    public void showEventsView(List<Event> eventList) {
        if(eventList == null || eventList.size() == 0){
            rlNoEvents.setVisibility(View.VISIBLE);
            tvPlaceholder.setText(R.string.find_event_no_event);
            rvEvents.setVisibility(View.GONE);
        } else {
            rlNoEvents.setVisibility(View.GONE);
            rvEvents.setVisibility(View.VISIBLE);

            eventAdapter.updateList(eventList);
        }
    }

    @Override
    public void showErrorMessage() {
        ToastUtil.toastLong(context, R.string.error_search_event_unsuccessful);
    }

    @Override
    public void showNoInternet() {
        ToastUtil.toastLong(context, R.string.error_no_internet);
    }

    @Override
    public void onEventClicked(Event event) {
        activity.openAboutEvent(Animation.LEFT, event);
    }


    @Override
    public void setLoadingIndicator(boolean active) {
        if(active){
            tvPlaceholder.setText(R.string.find_event_searching);
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        /* UNUSED */
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(!editable.toString().isEmpty()){
            presenter.findEventsByQuery(etSearch.getText().toString());
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        /* UNUSED */
    }
}
