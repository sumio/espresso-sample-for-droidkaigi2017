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

import android.content.res.Resources;
import android.support.annotation.IdRes;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.GeneralClickAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Tap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * A collection of {@link ViewAction}s and hamcrest matchers for {@link RecyclerView}.
 */
public class RecyclerViewUtils {

    private RecyclerViewUtils() {
        // can't instantiate this class.
    }

    /**
     * Returns an action that clicks a descendant of the view matched with the given resource id.
     */
    public static ViewAction clickDescendantViewWithId(@IdRes final int id) {
        return new ViewAction() {
            private Resources resources = null;

            @Override
            public Matcher<View> getConstraints() {
                return hasDescendant(withId(id));
            }

            @Override
            public String getDescription() {
                String idDesc = Integer.toString(id);
                if (resources != null) {
                    try {
                        idDesc = resources.getResourceName(id);
                    } catch (Resources.NotFoundException e) {
                        // ignore.
                        // idDesc would use int value.
                    }
                }
                return "Click on a descendant view with id: " + idDesc;
            }

            @Override
            public void perform(UiController uiController, View view) {
                GeneralClickAction action = new GeneralClickAction(Tap.SINGLE, GeneralLocation.VISIBLE_CENTER, Press.FINGER);
                View target = view.findViewById(id);
                // getConstraints() guarantees that the target never be null.
                action.perform(uiController, target);
            }
        };
    }

    /**
     * <p>
     * Returns a matcher that matches an item view at the given position
     * in the RecyclerView matched by {@code recyclerViewMatcher}.
     * </p>
     * <p>
     * If the item view at the given position is not laid out,
     * the matcher returned by this method will not match anything.
     * Call {@link android.support.test.espresso.contrib.RecyclerViewActions#scrollToPosition(int)}
     * with the same position prior to calling this method
     * in order to ensure that the item view is laid out.
     * </p>
     * <pre><code>
     *     onView(withId(R.id.recyclerView).perform(RecyclerViewActions.scrollToPosition(position);
     * </code></pre>
     *
     * @param recyclerViewMatcher a matcher for RecyclerView containing the item view.
     * @param position            position of the item view in RecyclerView
     */
    public static Matcher<View> withItemViewAtPosition(final Matcher<View> recyclerViewMatcher, final int position) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                if (!(parent instanceof RecyclerView) || !recyclerViewMatcher.matches(parent)) {
                    return false;
                }
                RecyclerView.ViewHolder viewHolder = ((RecyclerView) parent).findViewHolderForAdapterPosition(position);
                return viewHolder != null && viewHolder.itemView.equals(view);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Item view at position " + position + " in recycler view ");
                recyclerViewMatcher.describeTo(description);
            }
        };
    }
}
