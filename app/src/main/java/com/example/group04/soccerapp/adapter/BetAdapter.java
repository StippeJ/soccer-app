
package com.example.group04.soccerapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group04.soccerapp.EventActivity;
import com.example.group04.soccerapp.R;
import com.example.group04.soccerapp.api.ApiHelper;
import com.example.group04.soccerapp.model.Bet;
import com.example.group04.soccerapp.model.BetList;
import com.example.group04.soccerapp.model.Event;
import com.example.group04.soccerapp.model.EventsResponse;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author Jan Stippe
 */
public class BetAdapter extends RecyclerView.Adapter<BetAdapter.BetViewHolder> {

    private List<Bet> betList;
    private final Context context;
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String BET_LIST = "betList";

    /**
     * Constructor
     * @param context Context of the activity
     */
    public BetAdapter(Context context) {
        this.context = context;
        this.betList = getBets();
    }

    @NonNull
    @Override
    public BetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View betView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match, parent, false);
        return new BetViewHolder(betView);
    }

    @Override
    public void onBindViewHolder(@NonNull BetViewHolder holder, int position) {
        Bet bet = betList.get(position);
        int eventId = bet.getEventId();

        // Set onClickListener that leads to the EventActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), EventActivity.class);
            intent.putExtra("eventId", eventId);
            v.getContext().startActivity(intent);
        });

        // Get details about the match from the API
        ApiHelper apiHelper = new ApiHelper();
        apiHelper.getSoccerRepo().getEventById(new Callback<EventsResponse>() {
            @Override
            public void onResponse(@NotNull Call<EventsResponse> call, @NotNull Response<EventsResponse> response) {
                if (response.isSuccessful()) {
                    EventsResponse er = response.body();
                    Event event = er.getEvents().get(0);

                    // Check if a result for the match is available
                    if (event.getIntAwayScore() != null && event.getIntHomeScore() != null) {
                        // Compare the score of the home team to the bet and set an according icon
                        if (bet.getScoreHome() == event.getIntHomeScore()) {
                            holder.imageBetHomeCorrect.setVisibility(View.VISIBLE);
                            holder.imageBetHomeIncorrect.setVisibility(View.INVISIBLE);
                        } else {
                            holder.imageBetHomeIncorrect.setVisibility(View.VISIBLE);
                            holder.imageBetHomeCorrect.setVisibility(View.INVISIBLE);
                        }

                        // Compare the score of the away team to the bet and set an according icon
                        if (bet.getScoreAway() == event.getIntAwayScore()) {
                            holder.imageBetAwayCorrect.setVisibility(View.VISIBLE);
                            holder.imageBetAwayIncorrect.setVisibility(View.INVISIBLE);
                        } else {
                            holder.imageBetAwayIncorrect.setVisibility(View.VISIBLE);
                            holder.imageBetAwayCorrect.setVisibility(View.INVISIBLE);
                        }
                    }

                    // Load the badges of the clubs
                    apiHelper.loadClubBadge(event.getIdHomeTeam(), holder.imageHomeTeam);
                    apiHelper.loadClubBadge(event.getIdAwayTeam(), holder.imageAwayTeam);

                    // Show information about the teams and the bet for the match
                    holder.homeClub.setText(event.getStrHomeTeam());
                    holder.awayClub.setText(event.getStrAwayTeam());
                    holder.matchResult.setText(String.format(Locale.getDefault(), "%d : %d", bet.getScoreHome(), bet.getScoreAway()));
                    holder.dateOfMatch.setText(event.getFormattedDateAndTime());
                }
            }

            @Override
            public void onFailure(@NotNull Call<EventsResponse> call, @NotNull Throwable t) {

            }
        }, eventId);
    }

    @Override
    public int getItemCount() {
        return betList.size();
    }

    /**
     * Remove an item from the list
     * @param position Position of the item in the list
     */
    public void deleteItem(int position) {
        betList.remove(position);
        notifyItemRemoved(position);
        saveBets();
    }

    /**
     * Getter for the context-property
     * @return Object of type context
     */
    public Context getContext() {
        return context;
    }

    /**
     * Read all saved bets from SharedPreferences
     * @return A list of bets
     */
    private List<Bet> getBets() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String betListString = sharedPreferences.getString(BET_LIST, "{'betList':[]}");

        Gson gson = new Gson();

        return gson.fromJson(betListString, BetList.class).getBetList();
    }

    /**
     * Save all bets which are currently in the list to SharedPreferences
     */
    private void saveBets() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();

        String jsonBets = gson.toJson(new BetList(betList));

        editor.putString(BET_LIST, jsonBets);
        editor.apply();
    }

    public void reloadData() {
        betList = getBets();

//        betList.add(new Bet(1019616, 1, 5));
//        betList.add(new Bet(1019620, 1, 3));
//        betList.add(new Bet(1019608, 1, 1));
//        betList.add(new Bet(1019614, 3, 3));

        notifyDataSetChanged();
    }

    public static class BetViewHolder extends RecyclerView.ViewHolder {

        TextView dateOfMatch;
        TextView homeClub;
        TextView matchResult;
        TextView awayClub;
        ImageView imageHomeTeam;
        ImageView imageAwayTeam;
        ImageView imageBetHomeCorrect;
        ImageView imageBetHomeIncorrect;
        ImageView imageBetAwayCorrect;
        ImageView imageBetAwayIncorrect;

        public BetViewHolder(@NonNull View itemView) {
            super(itemView);

            dateOfMatch = itemView.findViewById(R.id.matchDate);
            homeClub = itemView.findViewById(R.id.homeClub);
            matchResult = itemView.findViewById(R.id.matchEndResult);
            awayClub = itemView.findViewById(R.id.awayClub);
            imageHomeTeam = itemView.findViewById(R.id.imageHomeTeamMain);
            imageAwayTeam = itemView.findViewById(R.id.imageAwayTeamMain);
            imageBetHomeCorrect = itemView.findViewById(R.id.betHomeCorrect);
            imageBetHomeIncorrect = itemView.findViewById(R.id.betHomeIncorrect);
            imageBetAwayCorrect = itemView.findViewById(R.id.betAwayCorrect);
            imageBetAwayIncorrect = itemView.findViewById(R.id.betAwayIncorrect);
        }
    }

}
