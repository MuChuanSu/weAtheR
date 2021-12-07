package com.example.weather;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

//I think when using Junit5, I should import org.junit.jupiter.api.Test instead, but if I do that
//It just gives me no runnable method error


public class MainActivityTest {
    private String notFoundToast = "Check your spelling";
    private String blankToast = "City name can't be blank";

    public @Rule
    ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mAct = null;

    @Test
    public void testLaunchDisplayAct(){
        Instrumentation.ActivityMonitor mon=
                getInstrumentation().addMonitor(displayInfo.class.getName(),null,false);
        mAct = mainActivityActivityTestRule.getActivity();
        assertNotNull(mAct.findViewById(R.id.search_button));
        //
        onView(withId(R.id.search_Bar)).perform(typeText("glasgow"));
        onView(withId(R.id.search_button)).perform(click());
        //test when typed with a correct city name and tap the button, can jump to next activity
        Activity act = getInstrumentation().waitForMonitorWithTimeout(mon,5000);
        assertNotNull(act);
        act.finish();
    }

    @Test
    public void testLaunchAboutAct(){
        Instrumentation.ActivityMonitor mon=
                getInstrumentation().addMonitor(displayInfo.class.getName(),null,false);
        mAct = mainActivityActivityTestRule.getActivity();
        assertNotNull(mAct.findViewById(R.id.aboutBTNID));
        //
        onView(withId(R.id.aboutBTNID)).perform(click());
        //test when typed with a correct city name and tap the button, can jump to next activity
        Activity act = getInstrumentation().waitForMonitorWithTimeout(mon,5000);
        assertNotNull(act);
        act.finish();
    }

    @Test
    public void testBlankInput(){
        Instrumentation.ActivityMonitor mon=
                getInstrumentation().addMonitor(displayInfo.class.getName(),null,false);
        mAct = mainActivityActivityTestRule.getActivity();
        assertNotNull(mAct.findViewById(R.id.search_button));
        //
        onView(withId(R.id.search_Bar)).perform(typeText(""));
        onView(withId(R.id.search_button)).perform(click());
        //test when typed nothing and tap the button, the toast will pop up
        onView(withText(blankToast)).inRoot(withDecorView(not(mainActivityActivityTestRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void testFalseCityName(){
        Instrumentation.ActivityMonitor mon=
                getInstrumentation().addMonitor(displayInfo.class.getName(),null,false);
        mAct = mainActivityActivityTestRule.getActivity();
        assertNotNull(mAct.findViewById(R.id.search_button));
        //
        onView(withId(R.id.search_Bar)).perform(typeText("rrr"));
        onView(withId(R.id.search_button)).perform(click());
        //test when typed with a false city name and tap the button, the toast will pop up
        onView(withText(notFoundToast)).inRoot(withDecorView(not(mainActivityActivityTestRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }
    @Test
    public void testLaunchARAct(){
        Instrumentation.ActivityMonitor mon=
                getInstrumentation().addMonitor(displayInfo.class.getName(),null,false);
        mAct = mainActivityActivityTestRule.getActivity();
        assertNotNull(mAct.findViewById(R.id.search_button));
        //
        onView(withId(R.id.search_Bar)).perform(typeText("glasgow"));
        onView(withId(R.id.search_button)).perform(click());
        //test when typed with a correct city name and tap the button, can jump to next activity
        onView(withId(R.id.arButtonId)).perform(click());
        Activity act = getInstrumentation().waitForMonitorWithTimeout(mon,2000);
        assertNotNull(act);
        act.finish();
    }


    @AfterEach
    public void tearDown() {
        mAct=null;
    }
}