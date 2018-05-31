package xyz.eventstreamer.eventstreamer.ui.aboutevent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
import xyz.eventstreamer.eventstreamer.util.TimeUtil;
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
    private Gson gson;

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_description)
    TextView tvDescription;
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

    private Emitter.Listener onConnect = args -> activity.runOnUiThread(() -> {
        Log.d(TAG, "onConnect");
    });

    private Emitter.Listener onDisconnect = args -> activity.runOnUiThread(() -> {
        Log.d(TAG, "onDisconnect");
    });

    private Emitter.Listener onConnectError = args -> activity.runOnUiThread(() -> {
        Log.d(TAG, "onConnectError");
    });

    private Emitter.Listener onNewPost = args -> activity.runOnUiThread(() -> {
        JSONObject data = (JSONObject) args[0];

        try {
            Event event = gson.fromJson(data.toString(), Event.class);

            Post post = event.getObjava().get(event.getObjava().size() - 1);
            postList.add(post);
            if(postAdapter != null){
                rvPosts.smoothScrollToPosition(postList.size()-1);
                postAdapter.onUpdate(postList);
            }

        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

    });

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

        gson = new GsonBuilder().create();

        EventStreamer app = (EventStreamer) activity.getApplication();
        socket = app.getSocket();
        socket.on(Socket.EVENT_CONNECT, onConnect);
        socket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        socket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        socket.on(Constants.SOCKET_LISTENER, onNewPost);
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

                if((user == null || user.getTip().equalsIgnoreCase("Uporabnik"))
                        || (user != null && !event.getIdUporabnik().equalsIgnoreCase(user.getIdUporabnik()))){
                    vDividerComment.setVisibility(View.GONE);
                    ivCamera.setVisibility(View.GONE);
                    etComment.setVisibility(View.GONE);
                    ivSend.setVisibility(View.GONE);
                }

                tvToolbarTitle.setText(event.getNaziv());
                tvDate.setText(TimeUtil.generateCurrentTimeAndDateFromMillis(Long.valueOf(event.getDatum())));
                tvDescription.setText(event.getOpis());

                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setReverseLayout(true);
                layoutManager.setStackFromEnd(true);

                rvPosts.setLayoutManager(layoutManager);
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
        socket.off(Socket.EVENT_CONNECT, onConnect);
        socket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        socket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        socket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        socket.off(Constants.SOCKET_LISTENER, onNewPost);
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
            post.setIdDogodek(event.getIdDogodek());
            presenter.sendPost(post);
        }
    }

    @OnClick(R.id.iv_camera)
    public void onCameraClick(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, Constants.REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = null;
            if (extras != null) {
                try {

                    File f = new File(context.getCacheDir(), "slika");
                    f.createNewFile();

                    Bitmap bitmap = (Bitmap) extras.get("data");
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 2 /*ignored for PNG*/, bos);
                    byte[] bitmapdata = bos.toByteArray();

                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();

                    RequestBody eventId = RequestBody.create(MediaType.parse("text/plain"), event.getIdDogodek());

                    RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), f);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("slika", f.getName(), reqFile);

                    presenter.sendImage(eventId, body);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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

    @Override
    public void onPostSentSuccessfuly() {
        etComment.setText("");
        etComment.clearFocus();
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

    private File persistImage(Bitmap bitmap, String name) {
        File filesDir = activity.getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }
        return imageFile;
    }

}
