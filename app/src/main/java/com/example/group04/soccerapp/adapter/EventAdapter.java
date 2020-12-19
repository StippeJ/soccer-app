package com.example.group04.soccerapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group04.soccerapp.BaseActivity;
import com.example.group04.soccerapp.BetActivity;
import com.example.group04.soccerapp.R;
import com.example.group04.soccerapp.api.ApiHelper;
import com.example.group04.soccerapp.model.Event;

import java.util.List;
import java.util.Locale;

/**
 * @author Jan Stippe, André Bautz
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.NextEventViewHolder> {

    // List of items and class-type for the navigation through an intent
    List<Event> eventList;
    Class<? extends BaseActivity> activityType;

    /**
     * Constructor
     * @param eventList List of events
     * @param activityType Type of activity to navigate to
     */
    public EventAdapter(List<Event> eventList, Class<? extends BaseActivity> activityType) {
        this.eventList = eventList;
        this.activityType = activityType;
    }

    @NonNull
    @Override
    public NextEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View nextMatch = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventAdapter.NextEventViewHolder(nextMatch);
    }

    @Override
    public void onBindViewHolder(@NonNull NextEventViewHolder holder, int position) {
        Event event = eventList.get(position);

        // Set an onclicklistener for the item
        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, activityType);
            intent.putExtra("eventId", event.getIdEvent());
            context.startActivity(intent);
        });

        // Set the data for the Views
        holder.dateOfMatch.setText(event.getFormattedDateAndTime());
        holder.homeTeam.setText(event.getStrHomeTeam());
        holder.awayTeam.setText(event.getStrAwayTeam());
        if (activityType == BetActivity.class) {
            holder.matchResult.setText("- : -");
        } else {
            holder.matchResult.setText(String.format(Locale.getDefault(), "%d : %d", event.getIntHomeScore(), event.getIntAwayScore()));
        }

        // Load the images
        ApiHelper apiHelper = new ApiHelper();
        apiHelper.loadTeamBadge(event.getIdHomeTeam(), holder.imageHomeTeam);
        apiHelper.loadTeamBadge(event.getIdAwayTeam(), holder.imageAwayTeam);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    // Update the list of events
    public void updateData(List<Event> eventList) {
        this.eventList = eventList;
        notifyDataSetChanged();
    }

    public static class NextEventViewHolder extends RecyclerView.ViewHolder {

        TextView dateOfMatch;
        TextView homeTeam;
        TextView matchResult;
        TextView awayTeam;
        ImageView imageHomeTeam;
        ImageView imageAwayTeam;

        public NextEventViewHolder(@NonNull View itemView) {
            super(itemView);

            dateOfMatch = itemView.findViewById(R.id.matchDate);
            homeTeam = itemView.findViewById(R.id.homeTeam);
            matchResult = itemView.findViewById(R.id.matchEndResult);
            awayTeam = itemView.findViewById(R.id.awayTeam);
            imageHomeTeam = itemView.findViewById(R.id.imageHomeTeamMain);
            imageAwayTeam = itemView.findViewById(R.id.imageAwayTeamMain);
        }
    }

}
