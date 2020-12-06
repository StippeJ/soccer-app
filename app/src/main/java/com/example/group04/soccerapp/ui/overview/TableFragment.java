package com.example.group04.soccerapp.ui.overview;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.group04.soccerapp.R;
import com.example.group04.soccerapp.adapter.NextEventAdapter;
import com.example.group04.soccerapp.adapter.TableAdapter;
import com.example.group04.soccerapp.api.ApiHelper;
import com.example.group04.soccerapp.model.ClubTableData;
import com.example.group04.soccerapp.model.Event;
import com.example.group04.soccerapp.model.EventsResponse;
import com.example.group04.soccerapp.model.TableResponse;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author André Bautz
 */
public class TableFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static NextEventsFragment newInstance(int index) {
        NextEventsFragment fragment = new NextEventsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table, container, false);

        ApiHelper apiHelper = new ApiHelper();
        int actualYear = Calendar.getInstance().get(Calendar.YEAR);
        String actualSeason = actualYear + "-" + ++actualYear;

        apiHelper.getSoccerRepo().getTable(new Callback<TableResponse>() {
            @Override
            public void onResponse(Call<TableResponse> call, Response<TableResponse> response) {
                if(response.isSuccessful()) {
                    TableResponse tableResponse = response.body();
                    List<ClubTableData> tableList = tableResponse.getTable();
                    RecyclerView tableRecyclerView = view.findViewById(R.id.tableRecyclerView);
                    TableAdapter tableAdapter = new TableAdapter(tableList);
                    tableRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));
                    tableRecyclerView.setAdapter(tableAdapter);
                } else {
                    Log.d("TableFragment", "onResponse not successfull");
                }
            }

            @Override
            public void onFailure(Call<TableResponse> call, Throwable t) {
                Log.d("TableFragment", "onFailure");
            }
        }, actualSeason);

        return view;
    }
}