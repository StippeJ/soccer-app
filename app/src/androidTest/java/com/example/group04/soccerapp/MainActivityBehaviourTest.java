package com.example.group04.soccerapp;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityBehaviourTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void progressBarIsVisible() {
        onView(withId(R.id.mainProgressSpinner))
                .check(matches(notNullValue()))
                .check(matches(isDisplayed()));
    }

    @Test
    public void headingIsVisible() {
        onView(isRoot())
                .perform(TestHelper.waitId(R.id.mainNewsHeadline, TimeUnit.SECONDS.toMillis(15)))
                .check(matches(notNullValue()))
                .check(matches(isDisplayed()));
    }

    @Test
    public void recyclerViewIsVisible() {
        onView(isRoot())
                .perform(TestHelper.waitId(R.id.mainLastMatchResults, TimeUnit.SECONDS.toMillis(15)))
                .check(matches(notNullValue()))
                .check(matches(isDisplayed()));
    }

    @Test
    public void buttonIsVisible() {
        onView(isRoot())
            .perform(TestHelper.waitId(R.id.mainBundesligaButton, TimeUnit.SECONDS.toMillis(15)))
            .check(matches(notNullValue()))
            .check(matches(isDisplayed()));
    }

    @Test
    public void buttonClick() throws InterruptedException {
        Intents.init();
        Thread.sleep(5000);
        onView(withId(R.id.mainBundesligaButton)).perform(click());
        intended(hasComponent(OverviewActivity.class.getName()));
        Intents.release();
    }
}