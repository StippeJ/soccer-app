package com.example.group04.soccerapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group04.soccerapp.BetActivity;
import com.example.group04.soccerapp.R;
import com.example.group04.soccerapp.model.Event;

import java.util.List;

public class NextEventAdapter extends RecyclerView.Adapter<NextEventAdapter.NextEventViewHolder> {

    List<Event> eventList;

    public NextEventAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public NextEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View nextMatch = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match, parent, false);
        return new NextEventAdapter.NextEventViewHolder(nextMatch);
    }

    @Override
    public void onBindViewHolder(@NonNull NextEventViewHolder holder, int position) {
        Event event = eventList.get(position);
        int eventId = event.getIdEvent();

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, BetActivity.class);
            intent.putExtra("eventId", event.getIdEvent());
            context.startActivity(intent);
        });

        String[] dateParts = event.getDateEvent().split("-");
        String[] timeParts = event.getStrTime().split(":");
        holder.dateOfMatch.setText(String.format("%s.%s.%s, %s:%s", dateParts[2], dateParts[1], dateParts[0], timeParts[0], timeParts[1]));
        holder.homeClub.setText(event.getStrHomeTeam());
        holder.matchResult.setText("- : -");
        holder.awayClub.setText(event.getStrAwayTeam());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class NextEventViewHolder extends RecyclerView.ViewHolder {

        TextView dateOfMatch;
        TextView homeClub;
        TextView matchResult;
        TextView awayClub;

        public NextEventViewHolder(@NonNull View itemview) {
            super(itemview);

            dateOfMatch = itemview.findViewById(R.id.matchDate);
            homeClub = itemview.findViewById(R.id.homeClub);
            matchResult = itemview.findViewById(R.id.matchEndResult);
            awayClub = itemview.findViewById(R.id.awayClub);
        }
    }

}
