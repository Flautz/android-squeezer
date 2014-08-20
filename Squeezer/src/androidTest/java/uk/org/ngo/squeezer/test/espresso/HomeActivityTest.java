package uk.org.ngo.squeezer.test.espresso;

import com.google.android.apps.common.testing.ui.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

import java.util.Map;

import uk.org.ngo.squeezer.HomeActivity;
import uk.org.ngo.squeezer.R;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;

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
        onView(withId(R.id.item_list))
                .check(matches(withAdaptedData(withItemContent("item: 168"))));
    }

    public static Matcher<Object> withItemContent(String expectedText) {
        checkNotNull(expectedText);
        return withItemContent(equalTo(expectedText));
    }

    public static Matcher<Object> withItemContent(final Matcher<String> itemTextMatcher){
        checkNotNull(itemTextMatcher);
        return new BoundedMatcher<Object, Map>(Map.class) {
            @Override
            public boolean matchesSafely(Map map) {
                return hasEntry(equalTo("STR"), itemTextMatcher).matches(map);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with item content: ");
                itemTextMatcher.describeTo(description);
            }
        };
    }

    private static Matcher<View> withAdaptedData(final Matcher<Object> dataMatcher) {
        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("with class name: ");
                dataMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof AdapterView)) {
                    return false;
                }
                @SuppressWarnings("rawtypes")
                Adapter adapter = ((AdapterView) view).getAdapter();
                for (int i = 0; i < adapter.getCount(); i++) {
                    if (dataMatcher.matches(adapter.getItem(i))) {
                        return true;
                    }
                }
                return false;
            }
        };
    }
}