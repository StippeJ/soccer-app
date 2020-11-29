package com.example.group04.soccerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group04.soccerapp.model.LastMatch;

import java.util.ArrayList;

public class LastMatchAdapter extends RecyclerView.Adapter<LastMatchAdapter.LastMatchViewHolder> {

    public ArrayList<LastMatch> listLastMatches;

    public LastMatchAdapter(ArrayList<LastMatch> listLastMatches) {
        this.listLastMatches = listLastMatches;
    }

    @NonNull
    @Override
    public LastMatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View lastMatchView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match, parent, false);
        return new LastMatchViewHolder(lastMatchView);
    }

    @Override
    public void onBindViewHolder(@NonNull LastMatchViewHolder holder, int position) {
        LastMatch lastMatch = listLastMatches.get(position);
        holder.dateOfMatch.setText(lastMatch.getDateOfMatch().toString());
        holder.homeClub.setText(lastMatch.getHomeClub());
        holder.matchResult.setText(lastMatch.getHomeScore() + ":" + lastMatch.getAwayScore());
        holder.awayClub.setText(lastMatch.getAwayClub());
    }

    @Override
    public int getItemCount() {
        return listLastMatches.size();
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
