package com.example.rvadam.projets1.ListFlowsByRouter;

import com.example.rvadam.projets1.model.Network;
import com.example.rvadam.projets1.model.Router;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;

/**
 * Created by rvadam on 13/02/2018.
 */

public class ListFlowsByRouterActivityPresenterTest {

    private static List<String> expectedListNamesOfRouters;
    private static List<Router> lr;

    ListFlowsByRouterActivityPresenter mListFlowsByRouterActivityPresenter;

    @Mock
    private ListFlowsByRouterActivityContract.View mListFlowsByRouterActivityView;

    private static <String> Matcher<List<String>> sameListOfRouterNames(final List<String> expectedList) {
        return new BaseMatcher<List<String>>(){
            @Override
            public boolean matches(Object o) {
                List<String> actualList = Collections.EMPTY_LIST;
                try {
                    actualList = (List<String>) o;
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
        lr= Network.getInstance().getListOfCurrentRouters();
        lr.clear();
        Router r1=new Router("1","Router1");
        Router r2=new Router("2","Router2");
        lr.add(r1);
        lr.add(r2);

        expectedListNamesOfRouters.add("Router1");
        expectedListNamesOfRouters.add("Router2");

        MockitoAnnotations.initMocks(this);
        mListFlowsByRouterActivityPresenter=new ListFlowsByRouterActivityPresenter(mListFlowsByRouterActivityView);
    }

    @Test
    public void testUpdateContentSpinnerToolbar(){
        mListFlowsByRouterActivityPresenter.updateContentSpinnerToolbar();
        verify(mListFlowsByRouterActivityView).showListOfRoutersInToolbar(argThat(sameListOfRouterNames(expectedListNamesOfRouters)));
    }
}
