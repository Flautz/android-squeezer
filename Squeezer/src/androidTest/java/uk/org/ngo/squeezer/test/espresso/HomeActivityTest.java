package uk.org.ngo.squeezer.test.espresso;

import android.test.ActivityInstrumentationTestCase2;

import uk.org.ngo.squeezer.HomeActivity;
import uk.org.ngo.squeezer.R;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static uk.org.ngo.squeezer.test.espresso.IconRowAdapterMatchers.withAdaptedData;
import static uk.org.ngo.squeezer.test.espresso.IconRowAdapterMatchers.withItemContent;

/**
 * https://code.google.com/p/android-test-kit/wiki/EspressoStartGuide
 * http://developer.android.com/reference/android/test/ActivityInstrumentationTestCase2.html
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 * <p/>
 */
public class HomeActivityTest extends ActivityInstrumentationTestCase2<HomeActivity> {
    public HomeActivityTest() {
        super(HomeActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testHomeActivityMenu() {
        // Example: Test that the first view in the list shows "Artists".
        onData(anything())
                .inAdapterView(withId(R.id.item_list))
                .atPosition(0)
                .check(matches(hasDescendant(
                        allOf(withId(R.id.text1), withText(containsString("Artists"))))));

        // Example: Test that there is an item in the adapter that contains the string
        // "Albums" (does not care where in the adapter it is).
        onView(withId(R.id.item_list))
                .check(matches(withAdaptedData(withItemContent("Albums"))));

        // Example: Test that the string "Test entry" does not appear in the adapter.
        onView(withId(R.id.item_list))
                .check(matches(not(withAdaptedData(withItemContent("Test entry")))));

        // Tests to implement

        // Inject a fake service that turns off/on all combinations of
        // - favorites
        // - music folder
        // - apps
        // - random play
        // and verify that the menu is correct for all of them
    }
}