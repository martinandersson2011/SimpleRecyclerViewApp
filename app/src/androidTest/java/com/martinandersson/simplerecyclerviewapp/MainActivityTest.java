package com.martinandersson.simplerecyclerviewapp;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;


/**
 * Created by martin.andersson on 7/29/15.
 * --- Getting started ---
 * https://code.google.com/p/android-test-kit/wiki/Espresso
 * https://code.google.com/p/android-test-kit/wiki/EspressoV2CheatSheet
 * --- Video tutorial ---
 * https://www.youtube.com/watch?v=TGU0B4qRlHY
 * --- Solving problem with Espresso and Dagger ---
 * http://www.donnfelker.com/android-studio-espresso-2-0-classnotfoundexception/
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void searchForPop() {
        // Change search text
        onView(withId(R.id.search_text)).perform(replaceText("pop"));

        // Click on search button
        onView(withId(R.id.search_button)).perform(click());

        // Check that we get some results
        onView(withId(R.id.no_results)).check(matches(not(isDisplayed())));

        // Check that progress bar is not displayed when done
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())));
    }

    @Test
    public void searchForNonExistingSongs() {
        // Change search text
        onView(withId(R.id.search_text)).perform(replaceText("somethingthatdoesnotexistabcdef"));

        // Click on search button
        onView(withId(R.id.search_button)).perform(click());

        // Check that we get some results
        onView(withId(R.id.no_results)).check(matches(isDisplayed()));

        // Check that progress bar is not displayed when done
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())));
    }

    @Test
    public void searchForJazzAndTestRecyclerView() {
        // Change search text
        onView(withId(R.id.search_text)).perform(replaceText("jazz"));

        // Click on search button
        onView(withId(R.id.search_button)).perform(click());

        // Scroll recycler view to the bottom
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollToPosition(49));

        // Scroll recycler view to the top
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollToPosition(0));

        // Click on the first item to start the detail view
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Check that the artist view on the DetailActivity is visible
        onView(withId(R.id.artist)).check(matches(isDisplayed()));

        // Go back to MainActivity
        pressBack();

        // Check that search button is displayed
        onView(withId(R.id.search_button)).check(matches(isDisplayed()));
    }



}
