package xyz.eventstreamer.eventstreamer.ui.aboutevent;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.eventstreamer.eventstreamer.R;
import xyz.eventstreamer.eventstreamer.model.Event;
import xyz.eventstreamer.eventstreamer.model.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostAdapterViewHolder> {

    private List<Post> postList;

    public PostAdapter(List<Post> postList){
        this.postList = postList;
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

    public void onUpdated(List<Post> postList){
        this.postList = postList;
        notifyDataSetChanged();
    }

    class PostAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_comment)
        TextView tvComment;

        PostAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(int position){
            Post post = postList.get(position);

            // Tukaj filaš podatke
        }

    }

}
