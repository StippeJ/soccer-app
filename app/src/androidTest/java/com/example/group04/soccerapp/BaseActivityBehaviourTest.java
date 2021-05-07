package com.example.group04.soccerapp;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BaseActivityBehaviourTest {

    @Rule
    public ActivityScenarioRule<BaseActivity> activityRule
            = new ActivityScenarioRule<>(BaseActivity.class);

    @Test
    public void clickMenu() {
        Espresso.openContextualActionModeOverflowMenu();

        onView(withId(R.id.menuItemAbout))
                .check(matches(has))
            .perform(click());
    }

}
