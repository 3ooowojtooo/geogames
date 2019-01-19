package com.kurocho.geogames;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;


import com.kurocho.geogames.views.MainActivity;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;



import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.NavigationViewActions.navigateTo;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class ProgressOverlayTests {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);
    private MainActivity mainActivity = mActivityRule.getActivity();


    @Test
    public void signUpTest(){
        delay();
        try {
            onView(allOf(withId(R.id.navigation_sign_out), isDisplayed())).perform(click());
        } catch (NoMatchingViewException ignored){} finally {
            onView(allOf(withId(R.id.navigation_sign_in),
                    isDisplayed())).perform(click());
            onView(withId(R.id.sign_in_sign_up_button))
                    .perform(click());
            onView(withId(R.id.sign_up_email))
                    .perform(typeText("test@androidespresso.com"), closeSoftKeyboard());
            onView(withId(R.id.sign_up_username))
                    .perform(typeText("testlogin"), closeSoftKeyboard());
            onView(withId(R.id.sign_up_password))
                    .perform(typeText("testpassword"), closeSoftKeyboard());
            onView(withId(R.id.sign_up_button)).perform(click());
            onView(withId(R.id.main_activity_progress_overlay))
                    .check(matches(isDisplayed()));
        }

    }

    @Test
    public void signInTest(){
        delay();
        try {
            onView(allOf(withId(R.id.navigation_sign_out), isDisplayed())).perform(click());
        } catch (NoMatchingViewException ignored){} finally {
            onView(allOf(withId(R.id.navigation_sign_in),
                    isDisplayed())).perform(click());
            delay();
            onView(withId(R.id.sign_in_username))
                    .perform(typeText("admin"), closeSoftKeyboard());
            onView(withId(R.id.sign_in_password))
                    .perform(typeText("Test1234"), closeSoftKeyboard());
            onView(withId(R.id.sign_in_button)).perform(click());
            onView(withId(R.id.main_activity_progress_overlay))
                    .check(matches(isDisplayed()));
        }

    }

    @Test
    public void searchTest(){
        delay();
        onView( allOf(withId(R.id.navigation_search),
                        isDisplayed())).perform(click());
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_search), withContentDescription("Search games"), isDisplayed()));
        actionMenuItemView.perform(click());
        ViewInteraction searchAutoComplete = onView(
                allOf(withId(R.id.search_src_text), isDisplayed()));
        searchAutoComplete.perform(replaceText("game"), closeSoftKeyboard());
        searchAutoComplete.perform(pressImeActionButton());
        onView(withId(R.id.main_activity_progress_overlay))
                .check(matches(isDisplayed()));
    }

    @Test
    public void downloadGameTest(){
        delay();
        onView( allOf(withId(R.id.navigation_search),
                isDisplayed())).perform(click());
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_search), withContentDescription("Search games"), isDisplayed()));
        actionMenuItemView.perform(click());
        ViewInteraction searchAutoComplete = onView(
                allOf(withId(R.id.search_src_text), isDisplayed()));
        searchAutoComplete.perform(replaceText("g"), closeSoftKeyboard());
        searchAutoComplete.perform(pressImeActionButton());
        delay(5000);
        onView(withText("download")).perform(click());
        onView(withId(R.id.main_activity_progress_overlay))
                .check(matches(isDisplayed()));
    }




    private static void delay(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void delay(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
