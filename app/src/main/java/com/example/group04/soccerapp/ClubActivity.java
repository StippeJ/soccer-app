package com.example.group04.soccerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.group04.soccerapp.model.ClubDetails;
import com.example.group04.soccerapp.model.ClubDetailsResponse;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Tim-Loris Deinert
 */
public class ClubActivity extends AppCompatActivity {

    ApiHelper apiHelper;
    //Create Api View variables
    TextView error;
    TextView teamName;
    ImageView teamBadge;
    ImageButton iconFb;
    ImageButton iconTw;
    ImageButton iconIn;
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
    ConstraintLayout socialIcons;
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
        setContentView(R.layout.activity_club);

        apiHelper = new ApiHelper();

        //get needed Views
        error = findViewById(R.id.errorText);
        teamName = findViewById(R.id.clubName);
        teamBadge = findViewById(R.id.clubImage);
        iconFb = findViewById(R.id.iconFacebook);
        iconTw = findViewById(R.id.iconTwitter);
        iconIn = findViewById(R.id.iconInstagram);
        foundedYear = findViewById(R.id.foundedYear);
        stadiumName = findViewById(R.id.stadiumName);
        stadiumImage = findViewById(R.id.stadiumImage);
        capacity = findViewById(R.id.capacityNumber);
        teamDescription = findViewById(R.id.clubDescription);
        infoHeading = findViewById(R.id.infoSubheading);
        foundedHeading = findViewById(R.id.foundedHeadline);
        stadiumHeading = findViewById(R.id.stadiumHeadline);
        capacityHeading = findViewById(R.id.capacityHeadline);
        descriptionHeading = findViewById(R.id.descriptionSubheading);
        socialIcons = findViewById(R.id.socialIcons);
        spacer1 = findViewById(R.id.spacer1);
        spacer2 = findViewById(R.id.spacer2);
    }


    /**
     * Get the explicit intent extra that contains the requested team id and load the provided Data into the given Views
     * @author Tim-Loris Deinert
     */
    @Override
    protected void onStart() {
        super.onStart();

        int clubId = getIntent().getIntExtra("clubId", 0);

        if(clubId != 0) {
            getClubDetails(clubId);
        }
        else {
            showError(true, false);
        }
    }

    /**
     * Request general Club Details from the API and load them into the Views
     * @param clubID ID of the requested Club
     * @author Tim-Loris Deinert
     */
    public void getClubDetails(int clubID) {
        apiHelper.getSoccerRepo().getClubDetails(new Callback<ClubDetailsResponse>() {
            @Override
            public void onResponse(@NotNull Call<ClubDetailsResponse> call, @NotNull Response<ClubDetailsResponse> response) {
                if(response.isSuccessful()) {
                    ClubDetailsResponse cdRes = response.body();
                    ClubDetails cd = cdRes.getTeams().get(0);
                    fillViews(cd);
                    Log.d("ClubActivity", "onResponse successful: " + cd.getStrAlternate());
                } else {
                    showError(false, false);
                    Log.d("ClubActivity", "onResponse not successful: " + response.code());
                }
            }

            public void onFailure(@NotNull Call<ClubDetailsResponse> call, @NotNull Throwable t) {
                showError(false, true);
                Log.d("ClubActivity", "onFailure");
            }
        }, clubID);
    }

    /**
     * Take the provided Data and load or write them into the layout views
     * @param cd API provided Data about one club
     * @author Tim-Loris Deinert
     */
    public void fillViews(ClubDetails cd) {
        //Fill the Views with the API Data
        teamName.setText(cd.getStrAlternate());
        Picasso.get().load(cd.getStrTeamBadge()).into(teamBadge);
        foundedYear.setText(String.valueOf(cd.getIntFormedYear()));
        stadiumName.setText(cd.getStrStadium());
        Picasso.get().load(cd.getStrStadiumThumb()).into(stadiumImage);
        capacity.setText(String.valueOf(cd.getIntStadiumCapacity()));

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
        infoHeading.setVisibility(View.INVISIBLE);
        foundedHeading.setVisibility(View.INVISIBLE);
        stadiumHeading.setVisibility(View.INVISIBLE);
        capacityHeading.setVisibility(View.INVISIBLE);
        descriptionHeading.setVisibility(View.INVISIBLE);
        socialIcons.setVisibility(View.INVISIBLE);
        spacer1.setVisibility(View.INVISIBLE);
        spacer2.setVisibility(View.INVISIBLE);

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
     * Method to refctor an url without https://www. at the beginning to a normal working url for an implicit intent
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