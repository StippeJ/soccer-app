package com.example.group04.soccerapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.Group;

import com.example.group04.soccerapp.api.ApiHelper;
import com.example.group04.soccerapp.model.TeamDetails;
import com.example.group04.soccerapp.model.TeamDetailsResponse;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Tim-Loris Deinert
 */
public class TeamActivity extends BaseActivity {

    ApiHelper apiHelper;
    //Create Api View variables
    Group contentGroup;
    ProgressBar progressBar;
    TextView error;
    TextView teamName;
    ImageView teamBadge;
    ImageView iconFb;
    ImageView iconTw;
    ImageView iconIn;
    TextView foundedYear;
    TextView stadiumName;
    ImageView stadiumImage;
    TextView capacity;
    TextView teamDescription;
    //Create Headline View variables
    TextView infoHeading;
    TextView foundedHeading;
    TextView stadiumHeading;
    TextView capacityHeading;
    TextView descriptionHeading;
    View spacer1;
    View spacer2;


    /**
     * Bind the created views to the layout
     * @param savedInstanceState Saved state of the Activity
     * @author Tim-Loris Deinert
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        apiHelper = new ApiHelper();

        //get needed Views
        contentGroup = findViewById(R.id.teamContentGroup);
        progressBar = findViewById(R.id.teamProgressSpinner);
        error = findViewById(R.id.teamErrorText);
        teamName = findViewById(R.id.teamName);
        teamBadge = findViewById(R.id.teamImage);
        iconFb = findViewById(R.id.teamIconFacebook);
        iconTw = findViewById(R.id.teamIconTwitter);
        iconIn = findViewById(R.id.teamIconInstagram);
        foundedYear = findViewById(R.id.teamFoundedYear);
        stadiumName = findViewById(R.id.teamStadiumName);
        stadiumImage = findViewById(R.id.teamStadiumImage);
        capacity = findViewById(R.id.teamCapacityNumber);
        teamDescription = findViewById(R.id.teamDescription);
        infoHeading = findViewById(R.id.teamInfoSubheading);
        foundedHeading = findViewById(R.id.teamFoundedHeadline);
        stadiumHeading = findViewById(R.id.teamStadiumHeadline);
        capacityHeading = findViewById(R.id.teamCapacityHeadline);
        descriptionHeading = findViewById(R.id.teamDescriptionSubheading);
        spacer1 = findViewById(R.id.teamSpacerTop);
        spacer2 = findViewById(R.id.teamSpacerBottom);
    }

    /**
     * Get the explicit intent extra that contains the requested team id and load the provided Data into the given Views
     * @author Tim-Loris Deinert
     */
    @Override
    protected void onStart() {
        super.onStart();

        int teamId = getIntent().getIntExtra("teamId", 0);

        if(teamId != 0) {
            getTeamDetails(teamId);
        }
        else {
            showError(true, false);
        }
    }

    /**
     * Request general Team Details from the API and load them into the Views
     * @param teamID ID of the requested Team
     * @author Tim-Loris Deinert
     */
    public void getTeamDetails(int teamID) {
        contentGroup.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        apiHelper.getSoccerRepo().getTeamDetails(new Callback<TeamDetailsResponse>() {
            @Override
            public void onResponse(@NotNull Call<TeamDetailsResponse> call, @NotNull Response<TeamDetailsResponse> response) {
                if(response.isSuccessful()) {
                    TeamDetailsResponse cdRes = response.body();
                    TeamDetails cd = cdRes.getTeams().get(0);
                    fillViews(cd);
                    contentGroup.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    showError(false, false);
                }
            }

            public void onFailure(@NotNull Call<TeamDetailsResponse> call, @NotNull Throwable t) {
                showError(false, true);
            }
        }, teamID);
    }

    /**
     * Take the provided Data and load or write them into the layout views
     * @param cd API provided Data about one team
     * @author Tim-Loris Deinert
     */
    public void fillViews(TeamDetails cd) {
        //Fill the Views with the API Data
        teamName.setText(cd.getStrTeam());
        Picasso.get().load(cd.getStrTeamBadge()).into(teamBadge);
        foundedYear.setText(String.valueOf(cd.getIntFormedYear()));
        stadiumName.setText(cd.getStrStadium());
        capacity.setText(String.valueOf(cd.getIntStadiumCapacity()));

        //check if a stadium image is provided by the API
        if(TextUtils.isEmpty(cd.getStrStadiumThumb())) {
            //if no image is provided set a dummy image
            stadiumImage.setImageResource(R.drawable.ic_image_not_available);
        }
        else {
            //else show the provided image
            Picasso.get().load(cd.getStrStadiumThumb()).into(stadiumImage);
        }

        //Check System Language and if needed change Description Language
        if(Locale.getDefault().getLanguage().equals("de")) {
            if(TextUtils.isEmpty(cd.getStrDescriptionDE())) {
                if(TextUtils.isEmpty(cd.getStrDescriptionEN())) {
                    teamDescription.setText(R.string.noDescription);
                }
                else {
                    teamDescription.setText(cd.getStrDescriptionEN());
                }
            }
            else{
                teamDescription.setText(cd.getStrDescriptionDE());
            }
        }
        else {
            if(TextUtils.isEmpty(cd.getStrDescriptionEN())) {
                teamDescription.setText(R.string.noDescription);
            }
            else {
                teamDescription.setText(cd.getStrDescriptionEN());
            }
        }

        //Check if social media links are provided and set Onclick listeners to the Image Buttons...
        //...for Facebook
        if(TextUtils.isEmpty(cd.getStrFacebook())) {
            iconFb.setVisibility(View.INVISIBLE);
        }
        else {
            iconFb.setOnClickListener((v) -> {
                //Implicit Intent to open the provided Facebook URL
                Intent implicitInt = new Intent(Intent.ACTION_VIEW);

                String url = cd.getStrFacebook();
                url = refactorURL(url);

                //Send Intent Data for the Browser
                implicitInt.setData(Uri.parse(url));
                startActivity(Intent.createChooser(implicitInt, "Select a Browser to open the Facebook Link"));
            });
        }

        //...for Twitter
        if(TextUtils.isEmpty(cd.getStrTwitter())) {
            iconTw.setVisibility(View.INVISIBLE);
        }
        else {
            iconTw.setOnClickListener((v) -> {
                //Implicit Intent to open the provided Twitter URL
                Intent implicitInt = new Intent(Intent.ACTION_VIEW);

                String url = cd.getStrTwitter();
                url = refactorURL(url);

                //Send Intent Data for the Browser
                implicitInt.setData(Uri.parse(url));
                startActivity(Intent.createChooser(implicitInt, "Select a Browser to open the Twitter Link"));
            });
        }

        //...for Instagram
        if(TextUtils.isEmpty(cd.getStrInstagram())) {
            iconIn.setVisibility(View.INVISIBLE);
        }
        else {
            iconIn.setOnClickListener((v) -> {
                //Implicit Intent to open the provided Instagram URL
                Intent implicitInt = new Intent(Intent.ACTION_VIEW);

                //Get the
                String url = cd.getStrInstagram();
                url = refactorURL(url);

                //Send Intent Data for the Browser
                implicitInt.setData(Uri.parse(url));
                startActivity(Intent.createChooser(implicitInt, "Select a Browser to open the Instagram Link"));
            });
        }
    }

    /**
     * Set the visibility of the Error TextView to visible and show a error message
     * @param onStart boolean value to indicate if the error occured in the onStart method or not to set an individual error message
     * @param onFailure boolean value to indicate if the error occured in the onFailure method or not to set an individual error message
     * @author Tim-Loris Deinert
     */
    public void showError(Boolean onStart, Boolean onFailure) {
        //set error message visibility
        error.setVisibility(View.VISIBLE);
        //set headline visibilities
        contentGroup.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        if(onStart) {
            error.setText(R.string.getExtraError);
        }
        else {
            if (onFailure) {
                error.setText(R.string.apiErrorOnFailure);
            } else {
                error.setText(R.string.apiResponseError);
            }
        }
    }

    /**
     * Method to refactor an url without https://www. at the beginning to a normal working url for an implicit intent
     * @param url url string which should be refactored to a full URL
     * @return full refactored url which can be used in imlicit intents
     * @author Tim-Loris Deinert
     */
    public String refactorURL(String url) {
        if (!url.contains("https://www")) {
            if(!url.contains("www")) {
                url = "https://www." + url;
            }
            else {
                url = "https://" + url;
            }
        }
        return url;
    }

}