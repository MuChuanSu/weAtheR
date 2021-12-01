package com.example.weather;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
//I think when using Junit5, I should import org.junit.jupiter.api.Test instead, but if I do that
//It just gives me no runnable method error
import org.junit.jupiter.api.AfterEach;


public class MainActivityTest {

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
        onView(withId(R.id.search_button)).perform(click());
        Activity act = getInstrumentation().waitForMonitorWithTimeout(mon,5000);
        assertNotNull(act);
        act.finish();
    }

    @AfterEach
    public void tearDown() {
        mAct=null;
    }
}