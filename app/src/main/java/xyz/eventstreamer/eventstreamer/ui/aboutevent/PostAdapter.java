package xyz.eventstreamer.eventstreamer.ui.aboutevent;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.eventstreamer.eventstreamer.BuildConfig;
import xyz.eventstreamer.eventstreamer.R;
import xyz.eventstreamer.eventstreamer.model.Event;
import xyz.eventstreamer.eventstreamer.model.Post;
import xyz.eventstreamer.eventstreamer.util.TimeUtil;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostAdapterViewHolder> {

    private List<Post> postList;
    private String eventId;

    public PostAdapter(List<Post> postList, String eventId){
        this.postList = postList;
        this.eventId = eventId;
    }

    @NonNull
    @Override
    public PostAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.item_post;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(layoutId, parent, false);
        return new PostAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapterViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return postList == null ? 0 : postList.size();
    }

    public void onUpdate(List<Post> postList){
        this.postList = postList;
        notifyDataSetChanged();
    }

    class PostAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_comment)
        TextView tvComment;
        @BindView(R.id.iv_comment)
        ImageView ivComment;

        PostAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(int position){
            Post post = postList.get(position);

            tvTime.setText(TimeUtil.generateCurrentTimeFromMillis(Long.valueOf(post.getDatum())));
            if(post.getKomentar() != null && post.getKomentar().length() != 0){
                tvComment.setText(post.getKomentar());
            } else if(post.getSlika() != null && post.getSlika().length() != 0){
                Picasso.get().load(BuildConfig.SERVICE_URL + "/uploads/"+eventId+"/"+post.getSlika()).into(ivComment);
            }
        }

    }

}
