package com.example.group04.soccerapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group04.soccerapp.ClubActivity;
import com.example.group04.soccerapp.EventActivity;
import com.example.group04.soccerapp.R;
import com.example.group04.soccerapp.api.ApiHelper;
import com.example.group04.soccerapp.model.ClubTableData;
import com.example.group04.soccerapp.model.TableResponse;

import org.w3c.dom.Text;

import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author André Bautz
 */
public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {

    public List<ClubTableData> tableList;

    public TableAdapter(List<ClubTableData> tableList) {
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
        ClubTableData clubTableData = tableList.get(position);

        Integer clubID = clubTableData.getTeamId();
        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent openMatchdayActivity = new Intent(context , ClubActivity.class);
            openMatchdayActivity.putExtra("clubId", clubID);
            context.startActivity(openMatchdayActivity);
        });

        holder.tablePosition.setText(Integer.toString(position + 1));

        int actualYear = Calendar.getInstance().get(Calendar.YEAR);
        String actualSeason = actualYear + "-" + ++actualYear;

        ApiHelper apiHelper = new ApiHelper();
        apiHelper.getSoccerRepo().getTable(new Callback<TableResponse>() {
            @Override
            public void onResponse(Call<TableResponse> call, Response<TableResponse> response) {
                if(response.isSuccessful()) {
                    apiHelper.loadClubBadge(clubID, holder.clubIcon);
                }
            }

            @Override
            public void onFailure(Call<TableResponse> call, Throwable t) {

            }
        }, actualSeason);

        holder.clubName.setText(clubTableData.getName());

        String tempString;

        tempString = holder.itemView.getContext().getString(R.string.tableWinsText, clubTableData.getWin());
        holder.wins.setText(tempString);
        tempString = holder.itemView.getContext().getString(R.string.tableDrawText, clubTableData.getDraw());
        holder.draw.setText(tempString);
        tempString = holder.itemView.getContext().getString(R.string.tableLossText, clubTableData.getLoss());
        holder.loss.setText(tempString);

        holder.clubPoints.setText(Integer.toString(clubTableData.getWin() * 3 + clubTableData.getDraw()));
    }

    @Override
    public int getItemCount() {
        return tableList.size();
    }

    public static class TableViewHolder extends RecyclerView.ViewHolder {
        TextView tablePosition;
        ImageView clubIcon;
        TextView clubName;
        TextView wins;
        TextView draw;
        TextView loss;
        TextView clubPoints;

        public TableViewHolder(@NonNull View itemView) {
            super(itemView);

            tablePosition = itemView.findViewById(R.id.tablePosition);
            clubIcon = itemView.findViewById(R.id.tableClubIcon);
            clubName = itemView.findViewById(R.id.tableClubName);
            wins = itemView.findViewById(R.id.tableWins);
            draw = itemView.findViewById(R.id.tableDraws);
            loss = itemView.findViewById(R.id.tableLosses);
            clubPoints = itemView.findViewById(R.id.tablePoints);
        }
    }

}
