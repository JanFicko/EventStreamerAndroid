package xyz.eventstreamer.eventstreamer.ui.aboutevent;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import xyz.eventstreamer.eventstreamer.EventStreamer;
import xyz.eventstreamer.eventstreamer.R;
import xyz.eventstreamer.eventstreamer.commons.Animation;
import xyz.eventstreamer.eventstreamer.commons.Keys;
import xyz.eventstreamer.eventstreamer.model.Event;
import xyz.eventstreamer.eventstreamer.model.Post;
import xyz.eventstreamer.eventstreamer.model.User;
import xyz.eventstreamer.eventstreamer.ui.BaseFragment;
import xyz.eventstreamer.eventstreamer.ui.main.MainActivity;
import xyz.eventstreamer.eventstreamer.util.SharedPreferenceUtil;
import xyz.eventstreamer.eventstreamer.util.ToastUtil;

public class AboutEventFragment extends BaseFragment implements AboutEventContract.View {

    private MainActivity activity;
    private AboutEventContract.Presenter presenter;
    private PostAdapter postAdapter;
    private SharedPreferenceUtil sharedPreferenceUtil = EventStreamer.getInstance().getSharedPreferenceUtil();
    private Event event = new Event();
    private User user = new User();

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.rv_posts)
    RecyclerView rvPosts;
    @BindView(R.id.iv_no_posts)
    ImageView ivNoPosts;
    @BindView(R.id.v_divider_comment)
    View vDividerComment;
    @BindView(R.id.iv_camera)
    ImageView ivCamera;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.iv_send)
    ImageView ivSend;

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
            event = (Event) getArguments().getSerializable(Keys.KEY_EVENT);
            if(event != null){
                user = sharedPreferenceUtil.retrieveObject(Keys.KEY_USER, User.class);

                if(user == null || user.getTip().equalsIgnoreCase("Uporabnik")){
                    vDividerComment.setVisibility(View.GONE);
                    ivCamera.setVisibility(View.GONE);
                    etComment.setVisibility(View.GONE);
                    ivSend.setVisibility(View.GONE);
                }

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

    @OnClick(R.id.iv_send)
    public void onSendClick(){
        if(!etComment.getText().toString().isEmpty()){
            Post post = new Post();
            post.setKomentar(etComment.getText().toString());
            post.setIdObjava(event.getIdDogodek());
            post.setIdUporabnik(user.getIdUporabnik());
            presenter.sendPost(post);
        }
    }

    @OnClick(R.id.iv_camera)
    public void onCameraClick(){
        // TODO: Open camera and get file image
        /*User user = sharedPreferenceUtil.retrieveObject(Keys.KEY_USER, User.class);
        Post post = new Post();
        post.setKomentar(etComment.getText().toString());
        post.setIdObjava(event.getIdDogodek());
        post.setIdUporabnik(user.getIdUporabnik());
        presenter.sendPost(post);*/
    }

    @Override
    public void showPosts(List<Post> postList) {
        if(postList == null || postList.size() == 0){
            ivNoPosts.setVisibility(View.VISIBLE);
            rvPosts.setVisibility(View.INVISIBLE);
        } else {
            ivNoPosts.setVisibility(View.INVISIBLE);
            rvPosts.setVisibility(View.VISIBLE);
            postAdapter.onUpdate(postList);
        }
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
