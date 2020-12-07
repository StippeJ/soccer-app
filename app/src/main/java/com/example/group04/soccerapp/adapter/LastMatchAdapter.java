package com.example.group04.soccerapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group04.soccerapp.EventActivity;
import com.example.group04.soccerapp.R;
import com.example.group04.soccerapp.model.Event;

import java.util.List;

/**
 * @author André Bautz
 */
public class LastMatchAdapter extends RecyclerView.Adapter<LastMatchAdapter.LastMatchViewHolder> {

    public List<Event> eventList;

    public LastMatchAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public LastMatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View lastMatchView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match, parent, false);
        return new LastMatchViewHolder(lastMatchView);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull LastMatchViewHolder holder, int position) {
        Event event = eventList.get(position);
        int eventID = event.getIdEvent();
        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent openMatchdayActivity = new Intent(context , EventActivity.class);
            openMatchdayActivity.putExtra("eventId", eventID);
            context.startActivity(openMatchdayActivity);
        });

        holder.dateOfMatch.setText(event.getFormattedDateAndTime());
        holder.homeClub.setText(event.getStrHomeTeam());
        holder.matchResult.setText(String.format("%d : %d", event.getIntHomeScore(), event.getIntAwayScore()));
        holder.awayClub.setText(event.getStrAwayTeam());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class LastMatchViewHolder extends RecyclerView.ViewHolder {
        TextView dateOfMatch;
        TextView homeClub;
        TextView matchResult;
        TextView awayClub;

        public LastMatchViewHolder(@NonNull View itemview) {
            super(itemview);

            dateOfMatch = itemview.findViewById(R.id.matchDate);
            homeClub = itemview.findViewById(R.id.homeClub);
            matchResult = itemview.findViewById(R.id.matchEndResult);
            awayClub = itemview.findViewById(R.id.awayClub);
        }
    }
}
