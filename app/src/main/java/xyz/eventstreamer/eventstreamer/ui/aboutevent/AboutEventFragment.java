package xyz.eventstreamer.eventstreamer.ui.aboutevent;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import xyz.eventstreamer.eventstreamer.BuildConfig;
import xyz.eventstreamer.eventstreamer.EventStreamer;
import xyz.eventstreamer.eventstreamer.R;
import xyz.eventstreamer.eventstreamer.commons.Animation;
import xyz.eventstreamer.eventstreamer.commons.Constants;
import xyz.eventstreamer.eventstreamer.commons.Keys;
import xyz.eventstreamer.eventstreamer.data.AppDatabase;
import xyz.eventstreamer.eventstreamer.model.Event;
import xyz.eventstreamer.eventstreamer.model.Post;
import xyz.eventstreamer.eventstreamer.model.User;
import xyz.eventstreamer.eventstreamer.model.database.EventEntity;
import xyz.eventstreamer.eventstreamer.model.database.PostEntity;
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
    private static AppDatabase appDatabase;
    private List<Post> postList = new ArrayList<>();

    private Socket socket;
    {
        try {
            socket = IO.socket(BuildConfig.SOCKET_URL);
        } catch (URISyntaxException e) {}
    }

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

    private Emitter.Listener onNewMessage = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(() -> {
                JSONObject data = (JSONObject) args[0];
                Post post = new Post();
                try {
                    post.setKomentar(data.getString("comment"));
                    post.setKomentar(data.getString("image"));
                } catch (JSONException e) {
                    return;
                }
                postList.add(post);
                if(postAdapter != null){
                    postAdapter.onUpdate(postList);
                }
            });
        }
    };

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
        appDatabase = AppDatabase.getInstance(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        socket.on(Constants.SOCKET_LISTENER, onNewMessage);
        socket.connect();
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

    @Override
    public void onDestroy() {
        super.onDestroy();

        socket.disconnect();
        socket.off(Constants.SOCKET_LISTENER, onNewMessage);
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
            this.postList = postList;

            ivNoPosts.setVisibility(View.INVISIBLE);
            rvPosts.setVisibility(View.VISIBLE);
            postAdapter.onUpdate(postList);

            List<PostEntity> postEntityList = new ArrayList<>();
            for(Post post : postList){
                PostEntity postEntity = new PostEntity(
                        post.getIdObjava(),
                        post.getIdUporabnik(),
                        post.getKomentar(),
                        post.getSlika(),
                        post.getDatum(),
                        event.getIdDogodek()

                );
                postEntityList.add(postEntity);
            }

            new PostAsyncTask().execute(postEntityList);
        }
    }

    @Override
    public void showLocalPosts(List<Post> postList) {
        if(postList == null || postList.size() == 0){
            ivNoPosts.setVisibility(View.VISIBLE);
            rvPosts.setVisibility(View.INVISIBLE);
        } else {
            ivNoPosts.setVisibility(View.INVISIBLE);
            rvPosts.setVisibility(View.VISIBLE);
            postAdapter.onUpdate(postList);
        }
    }

    private static class PostAsyncTask extends AsyncTask<List<PostEntity>, Void, Void> {

        @SafeVarargs
        @Override
        protected final Void doInBackground(List<PostEntity>... lists) {
            appDatabase.postDao().bulkInsertPosts(lists[0]);
            return null;
        }
    }

    @Override
    public void showErrorMessage() {
        ToastUtil.toastLong(context, R.string.error_unknown);
    }
    @Override
    public void showNoInternet() {
        presenter.getLocalPosts(event.getIdDogodek());
    }
}
