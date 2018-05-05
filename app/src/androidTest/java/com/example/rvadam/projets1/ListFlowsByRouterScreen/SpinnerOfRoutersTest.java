package com.example.rvadam.projets1.ListFlowsByRouterScreen;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.rvadam.projets1.ListFlowsByRouter.ListFlowsByRouterActivity;
import com.example.rvadam.projets1.ListFlowsByRouter.ListFlowsByRouterFragment;
import com.example.rvadam.projets1.R;
import com.example.rvadam.projets1.adapters.CustomListAdapter;
import com.example.rvadam.projets1.adapters.CustomSpinnerAdapter;
import com.example.rvadam.projets1.model.Network;
import com.example.rvadam.projets1.model.Queue;
import com.example.rvadam.projets1.model.Router;
import com.example.rvadam.projets1.services.FirebaseService;

import junit.extensions.ActiveTestSuite;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.internal.deps.dagger.internal.Preconditions.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.when;

/**
 * Created by rvadam on 18/02/2018.
 */

public class SpinnerOfRoutersTest {

    private static Intent routerAdditionIntent;
    private static Intent routerRemovalIntent;
    private static List<Router> lr; //List of router in Network's singleton
    private static BroadcastReceiver bcReceiver;
    private static ListView listView;
    private static Spinner spinner;

    private static final String TAG="SpineerOfRoutersTest";

    @Rule
    public ActivityTestRule<ListFlowsByRouterActivity> mListFlowsByRouterActivity=new ActivityTestRule<ListFlowsByRouterActivity> (ListFlowsByRouterActivity.class){
        @Override
        protected void beforeActivityLaunched() {
            //We initialize the Network singleton before each launch of Activity, if we don't do
            //this (in @Before method for example), the data won't be load on the views
            super.beforeActivityLaunched();

            lr = Network.getInstance().getListOfCurrentRouters();
            lr.clear();
            Queue q1 = new Queue("1", 80, 100, 10, "12.3.4.5", "34.8.6.5", 100, 50, "input", "queue1", "UDP", "1");
            Queue q2 = new Queue("2", 800, 1000, 10, "15.3.4.5", "69.8.6.5", 100, 50, "input", "queue2", "TCP", "1");
            Queue q3 = new Queue("3", 8, 100, 10, "125.3.4.5", "69.8.6.25", 100, 50, "input", "queue3", "TCP", "2");
            Queue q4 = new Queue("4", 28, 200, 20, "127.3.4.5", "2.8.6.25", 200, 40, "input", "queue4", "TCP", "2");

            List<Queue> lqr1 = new ArrayList<Queue>();
            List<Queue> lqr2 = new ArrayList<Queue>();
            lqr1.add(q1);
            lqr1.add(q2);
            lqr2.add(q3);
            lqr2.add(q4);
            Router r1 = new Router("1", "Router1", lqr1);
            Router r2 = new Router("2", "Router2", lqr2);
            lr.add(r1);
            lr.add(r2);
        }
    };

    public static Matcher<Object> withItemContent(String expectedText) {
        checkNotNull(expectedText);
        return withItemContent(equalTo(expectedText));
    }

    public static Matcher<Object> withItemContent(final Matcher<String> itemTextMatcher) {
        checkNotNull(itemTextMatcher);
        return new BoundedMatcher<Object, String>(String.class) {
            @Override
            public boolean matchesSafely(String text) {
                return itemTextMatcher.matches(text);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with item content: ");
                itemTextMatcher.describeTo(description);
            }
        };
    }
    @Before
    public void prepareTest() {

        //We retrieve the spinner from the activity
        spinner=(Spinner) mListFlowsByRouterActivity.getActivity().findViewById(R.id.spinner);
        /*//reinitialization of the spinner
        CustomSpinnerAdapter spinnerAdapter= (CustomSpinnerAdapter) spinner.getAdapter();
        spinnerAdapter.notifyDataSetChanged();*/

        //We retrieve the list view from the ListFlowsByRouterFragment
        listView=(ListView) mListFlowsByRouterActivity.getActivity().findViewById(android.R.id.list);
        /*//reinitialization of the spinner
        CustomListAdapter listAdapter= (CustomListAdapter) listView.getAdapter();
        listAdapter.notifyDataSetChanged();*/

        //We retrieve the broadcast receiver from the activity
        bcReceiver=mListFlowsByRouterActivity.getActivity().getmMessageReceiver();
        //Mock of the intent given in the onReceive method for Router addition
        routerAdditionIntent= Mockito.mock(Intent.class);
        String routerAdditionMessage[]={null,null};// Simulation of the message sent by the broadcaster of FirebaseService
        when(routerAdditionIntent.getStringArrayExtra(FirebaseService.ROUTEUR_ADDITION)).thenReturn(routerAdditionMessage);

        //Mock of the intent given in the onReceive method for Router removal
        routerRemovalIntent=Mockito.mock(Intent.class);
        String routerRemovalMessage[]={"1",null};//We will remove the router 1 in both tests router removal
        when(routerRemovalIntent.getStringArrayExtra(FirebaseService.ROUTEUR_REMOVAL)).thenReturn(routerRemovalMessage);
    }

    //Test with no changes on the Network's list of router(we verify the spinner with the model we prepare in prepareTest method
    @Test
    public void displayOfSpinnerAndDisplayOfTheCorrectQueuesTest(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //We check the item list in the Router Spinner
        Log.i(TAG,"nb router in singleton "+lr.size());
        assertEquals(2,spinner.getAdapter().getCount());// 2 elements should be present
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(0).check(matches(withText("Router1")));
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).check(matches(withText("Router2")));
        //onData(anything()).inAdapterView(withId(R.id.nameRouter)).atPosition(0).check(matches(withText("Router1")));
        //onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(1).check(matches(withText("Router2")));
        //ListFlowsByRouterFragment frag=(ListFlowsByRouterFragment) mListFlowsByRouterActivity.getActivity().getFragmentManager().findFragmentById(R.id.listFragment_container);
        //frag.
Log.i(TAG,"name view "+mListFlowsByRouterActivity.getActivity().findViewById(R.id.nameCurrentRouter));
        //As soon as the activity is launched, the router at the first index of the router lists should be displayed with its queues (we only check the names of the related queues in the List View whose content is tested in DisplayedListOfQueuesTest)
        //onView(withId(R.id.nameCurrentRouter)).check(matches((withText(mListFlowsByRouterActivity.getActivity().getResources().getString(R.string.titleListView) +"Router1"))));
        onView(allOf(withId(R.id.nameCurrentRouter), withText(mListFlowsByRouterActivity.getActivity().getResources().getString(R.string.titleListView) +"Router1")));
        assertEquals(2,listView.getAdapter().getCount());//2 queues should be present in the list
        /*onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(0).
                onChildView(withId(R.id.nameTextView)).
                check(matches(withItemContent("queue1(input)")));*/
        /*onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(0).
                onChildView(withId(R.id.nameTextView)).
                check(matches(withText("queue1(input)")));*/
        onView(allOf(withId(R.id.nameTextView),withText("quereue1(input)"), withParent(withId(android.R.id.list))));
        /*onData(withItemContent("queue1(input)"))
                .inAdapterView(withId(android.R.id.list))
                .check(matches(startsWith("queue1(input)")));*/
        /*onData(withItemContent("queue2(input)"))
                .inAdapterView(withId(android.R.id.list))
                .atPosition(1)
                .check(matches(isDisplayed()));*/
        onView(allOf(withId(R.id.nameTextView),withText("queue2(input)"), withParent(withId(android.R.id.list))));

        //We select the second router and verify that the related list of Queues is shown and that the name of router also changed
        //onView(withId(R.id.spinner)).perform(click());
        assertEquals(2,listView.getAdapter().getCount());//2 queues should be present in the list
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());//Selection of the second router (router2)

        onView(allOf(withId(R.id.nameCurrentRouter), withText(mListFlowsByRouterActivity.getActivity().getResources().getString(R.string.titleListView) +"Router2")));
        /*onData(withItemContent("queue3(input)"))
                .inAdapterView(withId(android.R.id.list))
                .atPosition(0)
                .check(matches(isDisplayed()));*/
        onView(allOf(withId(R.id.nameTextView),withText("queue3(input)"), withParent(withId(android.R.id.list))));
        /*onData(withItemContent("queue4(input)"))
                .inAdapterView(withId(android.R.id.list))
                .atPosition(1)
                .check(matches(isDisplayed()));*/
        onView(allOf(withId(R.id.nameTextView),withText("queue4(input)"), withParent(withId(android.R.id.list))));

    }

    //Test with an addition of a router on the prepared model
    @Test
    public void updateOfRoutersSpinnerAfterRouterAdditionTest(){
        //We check the number of items in the spinner before the addition
       assertEquals(2,spinner.getAdapter().getCount());// 2 elements should be present

        //We now add a router with 1 queue
        Queue q5= new Queue("5", 28, 200, 20, "127.3.4.5", "2.8.6.25", 200, 40, "input", "queue5", "TCP", "3");
        List<Queue> lqr3=new ArrayList<Queue>();
        lqr3.add(q5);
        Router r3=new Router("3","Router3",lqr3);
        lr.add(r3);
        //Simulation of the broadcast message sent by FirebaseService
        bcReceiver=mListFlowsByRouterActivity.getActivity().getmMessageReceiver();
        bcReceiver.onReceive(mListFlowsByRouterActivity.getActivity(),routerAdditionIntent);//We simulate the call of the onReceive method after a broadcast from FirebaseService

        //We check again the number of items in the spinner: should be 3
        assertEquals(3,spinner.getAdapter().getCount());

        //We select the newly added router and check its list of Queues
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(2).perform(click());//Selection of the added router (router3)


        assertEquals(1,listView.getAdapter().getCount());

        onView(allOf(withId(R.id.nameCurrentRouter), withText(mListFlowsByRouterActivity.getActivity().getResources().getString(R.string.titleListView) +"Router2")));
        onData(withItemContent("queue5(input)"))
                .inAdapterView(withId(android.R.id.list))
                .atPosition(0)
                .check(matches(isDisplayed()));



    }

    //Test with the removal of the first router that is currently selected
    @Test
    public void updateOfRoutersSpinnerAfterCurrentRouterRemovalTest(){
        //By default router1 i selected as it is the first router in Network's singleton: we will remove it
        lr.remove(0);
        bcReceiver.onReceive(mListFlowsByRouterActivity.getActivity(),routerRemovalIntent);

        //The fragment containing the list view should be removed
        assertNull(listView);
        //We check the number of items in the spinner: should be 1 and there is only router 2
        assertEquals(1,spinner.getAdapter().getCount());
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(0).check(matches(withText("Router2")));

        //We check content of the list of queues when selecting router2
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(0).perform(click());

        checkRouter2ContentAfterRemovingRouter1();
    }

    //Test with a removal of a router on the prepared model
    @Test
    public void updateOfRoutersSpinnerAfterRouterRemovalTest(){

        //We select the router2 before
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());
        //The router 2 is displayed
        onView(allOf(withId(R.id.nameCurrentRouter), withText(mListFlowsByRouterActivity.getActivity().getResources().getString(R.string.titleListView) +"Router2")));
        //removal of router 1
        lr.remove(0);
        bcReceiver.onReceive(mListFlowsByRouterActivity.getActivity(),routerRemovalIntent);
        //On the fragment, nothing should change as the currently displayed router 2 is not removed
        checkRouter2ContentAfterRemovingRouter1();

    }

    public void checkRouter2ContentAfterRemovingRouter1(){
        onView(allOf(withId(R.id.nameCurrentRouter), withText(mListFlowsByRouterActivity.getActivity().getResources().getString(R.string.titleListView) +"Router2")));
        onData(withItemContent("queue3(input)"))
                .inAdapterView(withId(android.R.id.list))
                .atPosition(0)
                .check(matches(isDisplayed()));
        onData(withItemContent("queue4(input)"))
                .inAdapterView(withId(android.R.id.list))
                .atPosition(1)
                .check(matches(isDisplayed()));
    }

}
