package com.example.group04.soccerapp.ui.overview;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.group04.soccerapp.R;
import com.example.group04.soccerapp.adapter.TableAdapter;
import com.example.group04.soccerapp.api.ApiHelper;
import com.example.group04.soccerapp.model.TableResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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
    TableAdapter tableAdapter;

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

        RecyclerView recyclerView = view.findViewById(R.id.tableRecyclerView);
        ProgressBar progressBar = view.findViewById(R.id.tableProgressBar);
        TextView errorTextView = view.findViewById(R.id.tableError);

        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        ApiHelper apiHelper = new ApiHelper();
        int actualYear = Calendar.getInstance().get(Calendar.YEAR);
        String actualSeason = actualYear + "-" + ++actualYear;

        apiHelper.getSoccerRepo().getTable(new Callback<TableResponse>() {
            @Override
            public void onResponse(@NotNull Call<TableResponse> call, @NotNull Response<TableResponse> response) {
                if(response.isSuccessful()) {
                    TableResponse tableResponse = response.body();
                    tableAdapter.updateData(tableResponse.getTable());
                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    errorTextView.setText(getString(R.string.apiResponseError));
                    progressBar.setVisibility(View.INVISIBLE);
                    errorTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<TableResponse> call, @NotNull Throwable t) {
                errorTextView.setText(getString(R.string.apiErrorOnFailure));
                progressBar.setVisibility(View.INVISIBLE);
                errorTextView.setVisibility(View.VISIBLE);
            }
        }, actualSeason);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView tableRecyclerView = view.findViewById(R.id.tableRecyclerView);
        tableRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));
        tableAdapter = new TableAdapter(new ArrayList<>());
        tableRecyclerView.setAdapter(tableAdapter);
    }
}