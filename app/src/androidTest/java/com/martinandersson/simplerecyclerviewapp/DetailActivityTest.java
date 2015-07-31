package com.martinandersson.simplerecyclerviewapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.PositionAssertions.isBelow;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by martin.andersson on 7/30/15.
 * --- Getting started ---
 * https://code.google.com/p/android-test-kit/wiki/Espresso
 * https://code.google.com/p/android-test-kit/wiki/EspressoV2CheatSheet
 * http://developer.android.com/reference/android/support/test/espresso/contrib/RecyclerViewActions.html
 * --- Video tutorial ---
 * https://www.youtube.com/watch?v=TGU0B4qRlHY
 * --- Solving problem with Espresso and Dagger ---
 * http://www.donnfelker.com/android-studio-espresso-2-0-classnotfoundexception/
 * --- Espresso Tutorial including RecyclerView ---
 * https://androidresearch.wordpress.com/2015/04/04/an-introduction-to-espresso/
 */
@RunWith(AndroidJUnit4.class)
public class DetailActivityTest {

    @Rule
    public ActivityTestRule<DetailActivity> mActivityRule = new ActivityTestRule(DetailActivity.class);

    @Test
    public void testDetailActivityViewsDisplayed() {
        // Check that our views are displayed
        onView(withId(R.id.artist)).check(matches(isDisplayed()));
        onView(withId(R.id.song)).check(matches(isDisplayed()));
        onView(withId(R.id.image)).check(matches(isDisplayed()));

        // Check that the views are in this order: artist, song, image
        onView(withId(R.id.song)).check(isBelow(withId(R.id.artist)));
        onView(withId(R.id.image)).check(isBelow(withId(R.id.song)));
    }

}
