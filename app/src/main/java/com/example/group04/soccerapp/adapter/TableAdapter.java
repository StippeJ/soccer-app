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

import com.example.group04.soccerapp.R;
import com.example.group04.soccerapp.TeamActivity;
import com.example.group04.soccerapp.api.ApiHelper;
import com.example.group04.soccerapp.model.TableResponse;
import com.example.group04.soccerapp.model.TeamTableData;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author André Bautz
 */
public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {

    public List<TeamTableData> tableList;

    public TableAdapter(List<TeamTableData> tableList) {
        this.tableList = tableList;
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tableView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table, parent, false);
        return new TableViewHolder(tableView);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        TeamTableData teamTableData = tableList.get(position);

        Integer teamID = teamTableData.getTeamId();
        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent openMatchdayActivity = new Intent(context , TeamActivity.class);
            openMatchdayActivity.putExtra("teamId", teamID);
            context.startActivity(openMatchdayActivity);
        });

        holder.tablePosition.setText(String.format(Locale.getDefault(), "%d", position + 1));

        Calendar calendar = Calendar.getInstance();
        int actualYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        String actualSeason;
        if (currentMonth < 6) {
            actualSeason = String.format(Locale.getDefault(), "%d-%d", actualYear - 1, actualYear);
        } else {
            actualSeason = String.format(Locale.getDefault(), "%d-%d", actualYear, actualYear + 1);
        }

        ApiHelper apiHelper = new ApiHelper();
        apiHelper.getSoccerRepo().getTable(new Callback<TableResponse>() {
            @Override
            public void onResponse(@NotNull Call<TableResponse> call, @NotNull Response<TableResponse> response) {
                if(response.isSuccessful()) {
                    apiHelper.loadTeamBadge(teamID, holder.teamIcon);
                }
            }

            @Override
            public void onFailure(@NotNull Call<TableResponse> call, @NotNull Throwable t) {

            }
        }, actualSeason);

        holder.teamName.setText(teamTableData.getName());

        String tempString;

        tempString = holder.itemView.getContext().getString(R.string.tableWinsText, teamTableData.getWin());
        holder.wins.setText(tempString);
        tempString = holder.itemView.getContext().getString(R.string.tableDrawText, teamTableData.getDraw());
        holder.draw.setText(tempString);
        tempString = holder.itemView.getContext().getString(R.string.tableLossText, teamTableData.getLoss());
        holder.loss.setText(tempString);

        holder.teamPoints.setText(String.format(Locale.getDefault(), "%d", teamTableData.getWin() * 3 + teamTableData.getDraw()));
    }

    @Override
    public int getItemCount() {
        return tableList.size();
    }

    public void updateData(List<TeamTableData> teamTableData) {
        this.tableList = teamTableData;
        notifyDataSetChanged();
    }

    public static class TableViewHolder extends RecyclerView.ViewHolder {
        TextView tablePosition;
        ImageView teamIcon;
        TextView teamName;
        TextView wins;
        TextView draw;
        TextView loss;
        TextView teamPoints;

        public TableViewHolder(@NonNull View itemView) {
            super(itemView);

            tablePosition = itemView.findViewById(R.id.tablePosition);
            teamIcon = itemView.findViewById(R.id.tableTeamIcon);
            teamName = itemView.findViewById(R.id.tableTeamName);
            wins = itemView.findViewById(R.id.tableWins);
            draw = itemView.findViewById(R.id.tableDraws);
            loss = itemView.findViewById(R.id.tableLosses);
            teamPoints = itemView.findViewById(R.id.tablePoints);
        }
    }

}
