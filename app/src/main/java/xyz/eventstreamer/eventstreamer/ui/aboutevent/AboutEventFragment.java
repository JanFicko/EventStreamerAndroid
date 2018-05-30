package xyz.eventstreamer.eventstreamer.ui.aboutevent;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import xyz.eventstreamer.eventstreamer.R;
import xyz.eventstreamer.eventstreamer.commons.Animation;
import xyz.eventstreamer.eventstreamer.commons.Keys;
import xyz.eventstreamer.eventstreamer.model.Event;
import xyz.eventstreamer.eventstreamer.model.Post;
import xyz.eventstreamer.eventstreamer.ui.BaseFragment;
import xyz.eventstreamer.eventstreamer.ui.main.MainActivity;
import xyz.eventstreamer.eventstreamer.util.ToastUtil;

public class AboutEventFragment extends BaseFragment implements AboutEventContract.View {

    private MainActivity activity;
    private AboutEventContract.Presenter presenter;
    private PostAdapter postAdapter;

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.rv_posts)
    RecyclerView rvPosts;

    public static AboutEventFragment newInstance(Event event) {
        Bundle args = new Bundle();
        AboutEventFragment fragment = new AboutEventFragment();
        args.putSerializable(Keys.KEY_EVENT, event);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_about_event;
    }

    @Override
    public void setPresenter(AboutEventContract.Presenter presenter) {
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

        if(getArguments() != null) {
            Event event = (Event) getArguments().getSerializable(Keys.KEY_EVENT);
            if(event != null){
                tvToolbarTitle.setText(event.getNaziv());

                rvPosts.setLayoutManager(new LinearLayoutManager(context));
                rvPosts.setHasFixedSize(true);
                postAdapter = new PostAdapter(null, event.getIdDogodek());
                rvPosts.setAdapter(postAdapter);

                presenter.getPosts(event);
            } else {
                activity.openDashboard(Animation.RIGHT);
            }
        }
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
    public void showPosts(List<Post> postList) {
        postAdapter.onUpdate(postList);
    }

    @Override
    public void showErrorMessage() {
        ToastUtil.toastLong(context, R.string.error_unknown);
    }
    @Override
    public void showNoInternet() {
        ToastUtil.toastLong(context, R.string.error_no_internet);
    }
}
