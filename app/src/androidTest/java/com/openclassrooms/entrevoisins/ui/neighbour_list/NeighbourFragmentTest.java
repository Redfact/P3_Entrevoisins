package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.contrib.ViewPagerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.ViewPager;
import android.view.View;


import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;
import com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion;
import com.openclassrooms.entrevoisins.utils.WaitViewAction;

import junit.framework.TestCase;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withParentIndex;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
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
        //Perform click inside recycle view
        onView(allOf(withId(R.id.list_neighbours), hasFocus())).perform(RecyclerViewActions.actionOnItemAtPosition(itemIndex, click()));
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
//      onView(withId(R.id.container)).perform(swipeLeft());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()) ).check(RecyclerViewItemCountAssertion.withItemCount(2));
    }
}