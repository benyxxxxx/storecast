package com.mandel.fybertest;

import android.content.Intent;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.mandel.fybertest.activity.MainActivity;
import com.mandel.fybertest.activity.OffersActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.times;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

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
	public void testSuccess2Test() {

		onView(withId(R.id.button)).perform(click());
		SystemClock.sleep(3000);
		intended(hasComponent(OffersActivity.class.getName()));

	}
	
	@Test
	public void testFaulureTest() {

		onView(withId(R.id.apikey)).perform(clearText());
		onView(withId(R.id.apikey)).perform(typeText("111111"));
		onView(withId(R.id.button)).perform(click());
		SystemClock.sleep(2000);
		intended(hasComponent(OffersActivity.class.getName()), times(0));
	}
	
	@Test
	public void testSuccessTest() {
	    
		onView(withId(R.id.format)).perform(clearText());
		onView(withId(R.id.format)).perform(typeText("xml"));
		onView(withId(R.id.appid)).perform(clearText());
		onView(withId(R.id.appid)).perform(typeText("2070"));
		onView(withId(R.id.uid)).perform(clearText());
		onView(withId(R.id.uid)).perform(typeText("spiderman"));
		onView(withId(R.id.apikey)).perform(clearText());
		onView(withId(R.id.apikey)).perform(typeText("1c915e3b5d42d05136185030892fbb846c278927"));
		onView(withId(R.id.button)).perform(click());
		SystemClock.sleep(2000);
		intended(hasComponent(OffersActivity.class.getName()));

	}

    
    @Rule
    public IntentsTestRule<OffersActivity> mOffersActivity =
            new IntentsTestRule<OffersActivity>(OffersActivity.class);

}
