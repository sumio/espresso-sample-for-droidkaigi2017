/*
 * Copyright (C) 2017 TOYAMA Sumio <jun.nama@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package jp.jun_nama.espresso.commons;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * A collection of view matchers which is not provided by the official {@link android.support.test.espresso.matcher.ViewMatchers}.
 */

public class MoreViewMatchers {

    private MoreViewMatchers() {
        // can't instantiate this class.
    }

    /**
     * Returns a matcher that matches a <em>VISIBLE</em> view at the given position
     * in the {@link ViewGroup} matched by the given {@code parentMatcher}.
     */
    public static Matcher<View> childAtPosition(final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("VISIBLE child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return (parent instanceof ViewGroup)
                        && parentMatcher.matches(parent)
                        && (getVisibleChildPosition((ViewGroup) parent, view) == position);
            }

            private int getVisibleChildPosition(ViewGroup parent, View child) {
                int indexOnlyVisible = 0;
                for (int i = 0; i < parent.getChildCount(); i++) {
                    View c = parent.getChildAt(i);
                    if (c.getVisibility() != View.VISIBLE) {
                        // child is not VISIBLE.
                        // let's continue without incrementing indexOnlyVisible.
                        continue;
                    }
                    if (parent.getChildAt(i).equals(child)) {
                        return indexOnlyVisible;
                    }
                    indexOnlyVisible++;
                }
                return -1;
            }
        };
    }
}
