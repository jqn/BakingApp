package io.jqn.bakingapp;


import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.jqn.bakingapp.ui.RecipeListActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class SelectRecipeAndStepsTest {
    public static final String NUTELLA = "Nutella Pie";
    public static final String INTRO = "Recipe Introduction";
    public static final String TITLE = "Ingredients";
    @Rule
    public ActivityTestRule<RecipeListActivity> mActivitytestRule = new ActivityTestRule<>(RecipeListActivity.class);

    @Test
    public void nutellaFirstInRecipeList() {
        if(InstrumentationRegistry.getContext().getResources().getConfiguration().smallestScreenWidthDp <= 600) {
            // When app loads is Nutella Pie first in recipes list
            onView(withId(R.id.recipes_wrapper))
                    .perform(RecyclerViewActions.scrollToPosition(1));
        } else {
            // When app loads is Nutella Pie first in recipes list
            onView(withId(R.id.recipes_wrapper_tablet))
                    .perform(RecyclerViewActions.scrollToPosition(1));
        }


        onView(withText(NUTELLA)).check(matches(isDisplayed()));
    }

    @Test
    public void selectRecipeAndSteps() {
        if(InstrumentationRegistry.getContext().getResources().getConfiguration().smallestScreenWidthDp <= 600) {
            // if the user taps on the Nutella Pie card
            onView(withId(R.id.recipes_wrapper)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
            // does it begin with ingredients
            onView(withId(R.id.ingredients_title)).check(matches(withText(TITLE)));
        } else {
            // if the user taps on the Nutella Pie card
            onView(withId(R.id.recipes_wrapper_tablet)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
            // does it begin with recipe introduction
            onView(withId(R.id.step_description_title)).check(matches(withText(INTRO)));
        }

    }
}
