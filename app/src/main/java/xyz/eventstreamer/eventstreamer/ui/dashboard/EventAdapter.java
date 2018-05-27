package xyz.eventstreamer.eventstreamer.ui.dashboard;

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

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventAdapterViewHolder> {

    private List<Event> eventList;
    private OnEventClickListener onEventClickListener;

    public interface OnEventClickListener{
        void onEventClicked(Event event);
    }

    public EventAdapter(List<Event> eventList, OnEventClickListener onEventClickListener){
        this.eventList = eventList;
        this.onEventClickListener = onEventClickListener;
    }

    @NonNull
    @Override
    public EventAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.item_event;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(layoutId, parent, false);
        return new EventAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapterViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return eventList == null ? 0 : eventList.size();
    }

    class EventAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_event_title)
        TextView tvTitle;

        EventAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(int position){
            Event event = eventList.get(position);

            // Tukaj filaš podatke
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            onEventClickListener.onEventClicked(eventList.get(clickedPosition));
        }
    }

}
