/*
 * Copyright (c) 2015 Google Inc.  All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.org.ngo.squeezer.test.espresso;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.MenuItem;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import uk.org.ngo.squeezer.HomeActivity;
import uk.org.ngo.squeezer.R;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ArtistListActivityTest {
    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule = new ActivityTestRule<>(
            HomeActivity.class);

    private void clickArtists() {
        onData(anything()).atPosition(0).perform(click());
    }

    @Test
    public void verifyFirstArtist() {
        clickArtists();

        // First item should be "A-Ha".
        // Note: This is only true on Nik's test system. We need a hermetic test server.
        onData(anything())
                .atPosition(0)
                .check(matches(hasDescendant(
                        allOf(withId(R.id.text1), withText(containsString("A-Ha"))))));
    }

    @Test
    public void verifyContextMenu() {
        clickArtists();

        // Click the context menu for the first entry, verify the menu contains the
        // expected text.
        onData(anything())
                .atPosition(0)
                .onChildView(withId(R.id.context_menu)).perform(click());

        String contextMenuTitles[] = {
                "Browse albums", "Browse songs", "Play", "Add to playlist", "Download"};

        int i = 0;
        for (String title : contextMenuTitles) {
            onData(instanceOf(MenuItem.class))
                    .atPosition(i++)
                    .check(matches(hasDescendant(withText(title))));
        }
    }
}
