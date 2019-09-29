package com.example.triangleapp;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest{

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private MainActivity mainActivity = null;

    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityActivityTestRule.getActivity();
    }

    @Test
    public void testValidationChecks() {
        onView(withId(R.id.stringUserInput)).perform(clearText());
        onView(withId(R.id.stringUserInput))
                .perform(typeText("2,2,3"));
        assertTrue(mainActivity.displayText());

        onView(withId(R.id.stringUserInput)).perform(clearText());
        onView(withId(R.id.stringUserInput))
                .perform(typeText("3.1,2,2.5"));
        assertTrue(mainActivity.displayText());

        onView(withId(R.id.stringUserInput)).perform(clearText());
        onView(withId(R.id.stringUserInput))
                .perform(typeText("99,99,99"));
        assertTrue(mainActivity.displayText());

        onView(withId(R.id.stringUserInput)).perform(clearText());
        onView(withId(R.id.stringUserInput))
                .perform(typeText("1,1,1"));
        assertTrue(mainActivity.displayText());

        onView(withId(R.id.stringUserInput)).perform(clearText());
        onView(withId(R.id.stringUserInput))
                .perform(typeText("[2,1,2]"));
        assertTrue(mainActivity.displayText());
    }

    @Test
    public void testFailedValidationCheckSingleInput() {
        onView(withId(R.id.stringUserInput)).perform(clearText());
        onView(withId(R.id.stringUserInput))
                .perform(typeText("3"));
        assertFalse(mainActivity.displayText());

        onView(withId(R.id.stringUserInput)).perform(clearText());
        onView(withId(R.id.stringUserInput))
                .perform(typeText("-1"));
        assertFalse(mainActivity.displayText());

        onView(withId(R.id.stringUserInput)).perform(clearText());
        onView(withId(R.id.stringUserInput))
                .perform(typeText("0.5,"));
        assertFalse(mainActivity.displayText());

        onView(withId(R.id.stringUserInput)).perform(clearText());
        onView(withId(R.id.stringUserInput))
                .perform(typeText("101"));
        assertFalse(mainActivity.displayText());

        onView(withId(R.id.stringUserInput)).perform(clearText());
        onView(withId(R.id.stringUserInput))
                .perform(typeText("asdf"));
        assertFalse(mainActivity.displayText());

        onView(withId(R.id.stringUserInput)).perform(clearText());
        onView(withId(R.id.stringUserInput))
                .perform(typeText("3a4g,"));
        assertFalse(mainActivity.displayText());

        onView(withId(R.id.stringUserInput)).perform(clearText());
        onView(withId(R.id.stringUserInput))
                .perform(typeText(",-3a4g,"));
        assertFalse(mainActivity.displayText());
    }

    @Test
    public void testFailedValidationCheckDoubleInput() {
        onView(withId(R.id.stringUserInput)).perform(clearText());
        onView(withId(R.id.stringUserInput))
                .perform(typeText("3,4,"));
        assertFalse(mainActivity.displayText());

        onView(withId(R.id.stringUserInput)).perform(clearText());
        onView(withId(R.id.stringUserInput))
                .perform(typeText("-1,200"));
        assertFalse(mainActivity.displayText());

        onView(withId(R.id.stringUserInput)).perform(clearText());
        onView(withId(R.id.stringUserInput))
                .perform(typeText("sdf0.5,53"));
        assertFalse(mainActivity.displayText());

        onView(withId(R.id.stringUserInput)).perform(clearText());
        onView(withId(R.id.stringUserInput))
                .perform(typeText("54,,54,"));
        assertFalse(mainActivity.displayText());

        onView(withId(R.id.stringUserInput)).perform(clearText());
        onView(withId(R.id.stringUserInput))
                .perform(typeText("asdf,difj"));
        assertFalse(mainActivity.displayText());

        onView(withId(R.id.stringUserInput)).perform(clearText());
        onView(withId(R.id.stringUserInput))
                .perform(typeText("3a4g,-1000000000000"));
        assertFalse(mainActivity.displayText());

        onView(withId(R.id.stringUserInput)).perform(clearText());
        onView(withId(R.id.stringUserInput))
                .perform(typeText(",-3a4g,0.000000001"));
        assertFalse(mainActivity.displayText());
    }

    @Test
    public void testFailedValidationCheckTripleInput() {
        onView(withId(R.id.stringUserInput)).perform(clearText());
        onView(withId(R.id.stringUserInput))
                .perform(typeText("3,4,14"));
        assertFalse(mainActivity.displayText());

        onView(withId(R.id.stringUserInput)).perform(clearText());
        onView(withId(R.id.stringUserInput))
                .perform(typeText("-1,100,100"));
        assertFalse(mainActivity.displayText());

        onView(withId(R.id.stringUserInput)).perform(clearText());
        onView(withId(R.id.stringUserInput))
                .perform(typeText("sdf0.5,53,,,34"));
        assertFalse(mainActivity.displayText());

        onView(withId(R.id.stringUserInput)).perform(clearText());
        onView(withId(R.id.stringUserInput))
                .perform(typeText("54,,54,55"));
        assertFalse(mainActivity.displayText());

        onView(withId(R.id.stringUserInput)).perform(clearText());
        onView(withId(R.id.stringUserInput))
                .perform(typeText("asdf,difj,dfsddij"));
        assertFalse(mainActivity.displayText());

        onView(withId(R.id.stringUserInput)).perform(clearText());
        onView(withId(R.id.stringUserInput))
                .perform(typeText("3a4g,-1000000000000,78"));
        assertFalse(mainActivity.displayText());

        onView(withId(R.id.stringUserInput)).perform(clearText());
        onView(withId(R.id.stringUserInput))
                .perform(typeText(",-3a4g,0.000000001,0"));
        assertFalse(mainActivity.displayText());
    }

    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }
}