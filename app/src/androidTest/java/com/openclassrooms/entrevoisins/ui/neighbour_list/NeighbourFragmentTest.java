package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;
import com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.WaitViewAction.waitFor;
import static org.hamcrest.Matchers.allOf;


@RunWith(AndroidJUnit4.class)
public class NeighbourFragmentTest extends TestCase {
    private NeighbourApiService mApiService= DI.getNeighbourApiService();

    @Rule
    public ActivityTestRule<ListNeighbourActivity> listNeighbourActivity = new ActivityTestRule<>(ListNeighbourActivity.class);
    public int itemIndex;
    public Neighbour neighbour;
    public int listSize;

    @Before
    public void setup(){
        itemIndex=0;
        neighbour= mApiService.getNeighbours().get(itemIndex);
        listSize = mApiService.getNeighbours().size();

    }

    @Test
    public void testDetailsNeighbour() {
        Intents.init();
        //Perform click inside recycle view
        onView(allOf(withId(R.id.list_neighbours), hasFocus())).perform(RecyclerViewActions.actionOnItemAtPosition(itemIndex, click()));

        //Check if activity is correctly launched
        intended(allOf(hasComponent(ShowNeighbourActivity.class.getName())));
        Intents.release();

        // Check if name is correctly displayed
        onView(withId(R.id.UserName0)).check(matches(withText(neighbour.getName())));

        //Press return button
        onView(withId(R.id.ReturnButton)).perform(click());

        //Check if ListNeighbour Correctly appear
        onView(withId(R.id.container)).check(matches(isDisplayed()));
    }

    @Test
    public void testDeleteButton() {
        onView(allOf(withId(R.id.list_neighbours), hasFocus())).perform(RecyclerViewActions.actionOnItemAtPosition(itemIndex, new DeleteViewAction()));
        onView(allOf(withId(R.id.list_neighbours), hasFocus())).check(RecyclerViewItemCountAssertion.withItemCount(listSize-1));
    }

    @Test
    public void testFavButton(){
        onView(allOf(withId(R.id.list_neighbours), hasFocus())).perform(RecyclerViewActions.actionOnItemAtPosition(itemIndex, click()));
        onView(withId(R.id.FavoriteButton)).perform(click());
        pressBack();

        onView(allOf(withId(R.id.list_neighbours), hasFocus())).perform(RecyclerViewActions.actionOnItemAtPosition(itemIndex+1, click()));
        onView(withId(R.id.FavoriteButton)).perform(click());
        pressBack();

        onView(withId(R.id.container)).perform(swipeLeft());

        onView(isRoot()).perform(waitFor(2000));
        onView(allOf(withId(R.id.list_neighbours), hasFocus()) ).check(RecyclerViewItemCountAssertion.withItemCount(2));
    }
}