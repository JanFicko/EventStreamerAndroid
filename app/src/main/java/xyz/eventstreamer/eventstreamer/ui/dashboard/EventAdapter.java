package xyz.eventstreamer.eventstreamer.ui.dashboard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.eventstreamer.eventstreamer.R;
import xyz.eventstreamer.eventstreamer.model.Event;
import xyz.eventstreamer.eventstreamer.util.TimeUtil;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventAdapterViewHolder> {

    private Context context;
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
        context = parent.getContext();
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

    public void updateList(List<Event> updatedEventList){
        this.eventList = updatedEventList;
        notifyDataSetChanged();
    }

    class EventAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_event)
        ImageView ivEvent;
        @BindView(R.id.tv_event_title)
        TextView tvEventTitle;
        @BindView(R.id.tv_event_info)
        TextView tvEventInfo;

        EventAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(int position){
            Event event = eventList.get(position);

            /*if(TimeUtil.calculateMillisecondsIfLessThanOneDay(Long.valueOf(event.getDatum()))){
                ivEvent.setImageResource(R.drawable.ic_live_red);
            } else {
                ivEvent.setImageResource(R.drawable.ic_live_black);
            }*/
            ivEvent.setImageResource(R.drawable.ic_live_black);

            tvEventTitle.setText(event.getNaziv());

            // TimeUtil.generateBackendDateDateFromMillis(Long.valueOf(event.getDatum()))
            if(event.getKategorija().size() != 0){
                tvEventInfo.setText(
                        context.getString(
                                R.string.dashboard_event_fill_category,
                                event.getKategorija().get(0).getNaziv(),
                                "15. Mar 2018"
                        )
                );
            } else {
                tvEventInfo.setText(
                        context.getString(
                                R.string.dashboard_event_fill,
                                "15. Mar 2018"
                        )
                );
            }
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            onEventClickListener.onEventClicked(eventList.get(clickedPosition));
        }
    }

}
