package com.mandel.storecast;

import android.content.Intent;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.mandel.storecast.activity.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.pressKey;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.times;

import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import	android.view.KeyEvent;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>
{
	
        private MainActivity mActivity;

	public MainActivityTest() {
		super(MainActivity.class);
	}
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		injectInstrumentation(InstrumentationRegistry.getInstrumentation());
		mActivity = (MainActivity)getActivity();
	}

	
	@Test
	public void testScrollTest() {

		onView(withId(R.id.search)).perform(typeText("mobile"));
		SystemClock.sleep(2000);
		onView(withId(R.id.search)).perform(click(), pressKey(KeyEvent.KEYCODE_ENTER));
		SystemClock.sleep(4000);


		onView(withId(R.id.listview))
			.perform(scrollToPosition(10000));
		SystemClock.sleep(6000);
		
		onView(withId(R.id.listview))
			.perform(scrollToPosition(10));

		SystemClock.sleep(6000);
		
		onView(withId(R.id.listview)).check(matches(isDisplayed()));
	}
	
	@Test
	public void testEmpty() {

		onView(withId(R.id.search)).perform(typeText("ssdffghjkjkmobile"));
		SystemClock.sleep(2000);
		onView(withId(R.id.search)).perform(click(), pressKey(KeyEvent.KEYCODE_ENTER));
		SystemClock.sleep(4000);


		onView(withId(R.id.listview))
			.perform(scrollToPosition(100));
		SystemClock.sleep(6000);
		
		onView(withId(R.id.listview))
			.perform(scrollToPosition(10));

		SystemClock.sleep(6000);
		
		onView(withId(R.id.listview)).check(matches(isDisplayed()));
	}
	
}
