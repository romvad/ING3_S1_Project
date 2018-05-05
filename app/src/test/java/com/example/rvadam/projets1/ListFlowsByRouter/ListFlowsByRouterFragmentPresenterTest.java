package com.example.rvadam.projets1.ListFlowsByRouter;

import com.example.rvadam.projets1.model.Network;
import com.example.rvadam.projets1.model.Queue;
import com.example.rvadam.projets1.model.Router;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;

/**
 * Created by rvadam on 13/02/2018.
 */

public class ListFlowsByRouterFragmentPresenterTest {

    private static List<Queue> listOfExpectedQueues; // expected listOfQueues that is put in parameter when calling loadQueuesCurrent of the presenter

    @Mock
    ListFlowsByRouterFragmentContract.View mListFlowsByRouterFragmentView;

    ListFlowsByRouterFragmentPresenter mListFlowsByRouterFragmentPresenter;

    private static <Queue> Matcher<List<Queue>> sameListOfQueues(final List<Queue> expectedList) {
        return new BaseMatcher<List<Queue>>(){
            @Override
            public boolean matches(Object o) {
                List<Queue> actualList = Collections.EMPTY_LIST;
                try {
                    actualList = (List<Queue>) o;
                }
                catch (ClassCastException e) {
                    return false;
                }
                //Set<String> expectedSet = new HashSet<T>(expectedList);
                //Set<String> actualSet = new HashSet<T>(actualList);
                return actualList.equals(expectedList);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("should contain all and only elements of ").appendValue(expectedList);
            }
        };
    }

    @Before
    public void prepareTest(){
        List<Router> lr= Network.getInstance().getListOfCurrentRouters();
        Queue q1=new Queue("1",80,100,10,"12.3.4.5", "34.8.6.5",100, 50,"input", "queue1","UDP","1");
        Queue q2=new Queue("2",800,1000,10,"15.3.4.5", "69.8.6.5",100, 50,"input", "queue2","TCP","1");
        Queue q3=new Queue("3",8,100,10,"125.3.4.5", "69.8.6.25",100, 50,"input", "queue3","TCP","2");

        List<Queue> lqr1=new ArrayList<Queue>();
        List<Queue> lqr2=new ArrayList<Queue>();
        lqr1.add(q1);
        lqr1.add(q2);
        lqr2.add(q3);
        Router r1=new Router("1","Router1",lqr1);
        Router r2=new Router("2","Router2",lqr2);
        lr.add(r1);
        lr.add(r2);

        listOfExpectedQueues=lqr1;//Expected queues are the one of the Router1 as we will call the loadQueuesCurrent for the Router1

        MockitoAnnotations.initMocks(this);
        mListFlowsByRouterFragmentPresenter=new ListFlowsByRouterFragmentPresenter(mListFlowsByRouterFragmentView);

    }

    @Test
    public void testLoadCurrentQueues(){
       mListFlowsByRouterFragmentPresenter.loadQueuesCurrent(0);
       verify(mListFlowsByRouterFragmentView).showListOfFlows(argThat(sameListOfQueues(listOfExpectedQueues)));
    }

    @Test
    public void testShowLandscapeFragment(){
        mListFlowsByRouterFragmentPresenter.showLandscapeFragment(0,3);
        verify(mListFlowsByRouterFragmentView).instanciateLandscapeFragment(0,3);
    }

    @Test
    public void testShowPortraitFragment(){
        mListFlowsByRouterFragmentPresenter.showPortraitFragment(0,3);
        verify(mListFlowsByRouterFragmentView).instanciatePortraitFragment(0,3);
    }

}
