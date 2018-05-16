package com.udacity.gradle.builditbigger;

import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.concurrent.TimeUnit;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
/** Based on: https://github.com/chiuki/espresso-samples/blob/a70a343862a4199804f6fdffd9173cd453bb8d11/
 * idling-resource-elapsed-time/app/src/androidTest/java/com/sqisland/espresso/idling_resource/
 * elapsed_time/MainActivityTest.java
 * */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void resetTimeout() {
        IdlingPolicies.setMasterPolicyTimeout(60, TimeUnit.SECONDS);
        IdlingPolicies.setIdlingResourceTimeout(26, TimeUnit.SECONDS);
    }

    @Test
    public void checkAsyncTask(){
        IdlingResource idlingResource;
        long waitingTime = 10000;
        // Start
        onView(withId(R.id.button_tell_joke))
                .check(matches(withText(R.string.button_text)))
                .perform(click());

        // Make sure Espresso does not time out
        IdlingPolicies.setMasterPolicyTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);

        //Wait
        idlingResource = new com.udacity.gradle.builditbigger.ElapsedTimeIdlingResource(waitingTime);
        IdlingRegistry.getInstance().register(idlingResource);

        // Verify
        onView(withId(R.id.joke_text))
                .check(matches(isDisplayed()));

        //Clean up
        IdlingRegistry.getInstance().unregister(idlingResource);
    }
}