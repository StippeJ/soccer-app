package com.example.group04.soccerapp;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class OverviewActivityBehaviourTest {

    @Rule
    public ActivityScenarioRule<OverviewActivity> activityRule
            = new ActivityScenarioRule<>(OverviewActivity.class);

    @Test
    public void getIntent() {
        onView(isRoot())
                .perform(TestHelper.waitId(R.id.mainBundesligaButton, TimeUnit.SECONDS.toMillis(15)))
                .perform(click());

        intended(
                hasComponent(OverviewActivity.class.getName())
        );
    }

}
