package com.martinandersson.simplerecyclerviewapp;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;


/**
 * Created by martin.andersson on 7/29/15.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public final ActivityRule<MainActivity> main = new ActivityRule<>(MainActivity.class);

    @Test
    public void shouldBeAbleToLaunchMainScreen() {
        onView(withText("Search")).check(matches(isDisplayed()));
    }

    @Test
    public void searchForPop() {
        // Clear text and enter pop
        onView(withId(R.id.search_text)).perform(clearText());
        onView(withId(R.id.search_text)).perform(typeText("pop"));

        // Click on search button
        onView(withId(R.id.search_button)).perform(click());

        // Check that we get some results
        onView(withId(R.id.no_results)).check(matches(not(isDisplayed())));

        // Check that progress bar is not displayed when done
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())));
    }

    @Test
    public void searchForNonExistingSongs() {
        // Clear text and enter garbage text
        onView(withId(R.id.search_text)).perform(clearText());
        onView(withId(R.id.search_text)).perform(typeText("somethingthatdoesnotexistabcdef"));

        // Click on search button
        onView(withId(R.id.search_button)).perform(click());

        // Check that we get some results
        onView(withId(R.id.no_results)).check(matches(isDisplayed()));

        // Check that progress bar is not displayed when done
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())));
    }

}
