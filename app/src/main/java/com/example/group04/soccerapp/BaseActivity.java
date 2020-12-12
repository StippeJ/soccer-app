package com.example.group04.soccerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * @author Tim-Loris Deinert
 */
public class BaseActivity extends AppCompatActivity {
    private static final int DISPLAY_MODE_NIGHT = 1;
    private static final int DISPLAY_MODE_DAY = 0;
    protected static final String SHARED_PREFS = "sharedPrefs";
    protected static final String BET_LIST = "betList";

    /**
     * Create/inflate Options menu
     * @author Tim-Loris Deinert
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        //Check which mode is set to change the options menu items to the right mode
        if(isNightModeActive(BaseActivity.this)) {
            changeMenu(menu, DISPLAY_MODE_NIGHT);
        }
        else {
            changeMenu(menu, DISPLAY_MODE_DAY);
        }

        return true;
    }

    /**
     * gets Option menu functions on select for the submenu points
     * @param item from the user selected submenu item
     * @return boolean value if the item select was successful or not
     * @author Tim-Loris Deinert
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemAbout:
                //Print app info's about version and the creator
                Toast.makeText(this, R.string.toastAbout, Toast.LENGTH_LONG).show();
                return true;
            case R.id.menuItemBugReport:
                //Create an Intent to report a bug to a given email address and start it
                Intent sendMail = new Intent(Intent.ACTION_SEND);

                //Set the needed Attributes for the Intent
                sendMail.putExtra(Intent.EXTRA_EMAIL, new String[]{"bugReport.soccerApp@gmail.com"});
                sendMail.putExtra(Intent.EXTRA_SUBJECT, "SoccerApp - Bug Report");
                sendMail.putExtra(Intent.EXTRA_TEXT, "I have found the following Bug in your App: ");
                sendMail.setType("message/rfc822");

                //check if the user has an email app installed, if not show a Toast
                try {
                    startActivity(Intent.createChooser(sendMail, "Choose mail client"));
                } catch (android.content.ActivityNotFoundException exception) {
                    Toast.makeText(this, R.string.errorNoMailClients, Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.menuItemDisplayMode:
                //change the App theme to day or night mode
                if(isNightModeActive(BaseActivity.this)) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    Toast.makeText(this, R.string.toastModeChangedDay, Toast.LENGTH_SHORT).show();
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    Toast.makeText(this, R.string.toastModeChangedNight, Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.menuItemBugList:
                //Print bug list as toast
                Toast.makeText(this, R.string.toastBugList, Toast.LENGTH_LONG).show();
                return true;
            default:
                return false;
        }
    }

    /**
     * Method to check which mode (day or night/ light or dark mode) is activated
     * @param context current context
     * @return true if night mode is active and false if day mode is active
     * @author Tim-Loris Deinert
     */
    public static boolean isNightModeActive(Context context) {
        int defaultNightMode = AppCompatDelegate.getDefaultNightMode();
        if (defaultNightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            return true;
        }
        if (defaultNightMode == AppCompatDelegate.MODE_NIGHT_NO) {
            return false;
        }

        int currentNightMode = context.getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                return false;
            case Configuration.UI_MODE_NIGHT_YES:
                return true;
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                return false;
        }
        return false;
    }

    /**
     * Method to change the menu item style for the two modes (night and day mode)
     * @param menu menu which contains the menu item
     * @param mode integer which represents the current set mode
     * @author Tim-loris Deinert
     */
    public void changeMenu(Menu menu, int mode) {
        MenuItem displayMode = menu.findItem(R.id.menuItemDisplayMode);

        //Check which mode is set
        if(mode == DISPLAY_MODE_DAY) {
            //change the menu item to day mode configuration
            displayMode.setIcon(R.drawable.ic_menu_icon_night);
            displayMode.setTitle(R.string.modeDayText);
        }
        else {
            //change the menu item to night mode configuration
            displayMode.setIcon(R.drawable.ic_menu_icon_day);
            displayMode.setTitle(R.string.modeNightText);
        }
    }
}