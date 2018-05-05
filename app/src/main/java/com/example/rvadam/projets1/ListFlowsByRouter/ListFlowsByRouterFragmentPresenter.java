package com.example.rvadam.projets1.ListFlowsByRouter;

import com.example.rvadam.projets1.model.Network;
import com.example.rvadam.projets1.model.Queue;

import java.util.List;

import static com.example.rvadam.projets1.model.Network.getInstance;

/**
 * Created by rvadam on 06/02/2018.
 */

public class ListFlowsByRouterFragmentPresenter implements ListFlowsByRouterFragmentContract.UserActionsListener {

    private final ListFlowsByRouterFragmentContract.View mListFlowsByRouterFragmentView;

    public ListFlowsByRouterFragmentPresenter(ListFlowsByRouterFragmentContract.View mListFlowsByRouterFragmentView) {
        this.mListFlowsByRouterFragmentView = mListFlowsByRouterFragmentView;
    }

    @Override
    public void loadQueuesCurrent(int positionOfRouter) {
        List<Queue> loadedQueues=Network.getInstance().getListOfCurrentRouters().get(positionOfRouter).getQueues();
        mListFlowsByRouterFragmentView.showListOfFlows(loadedQueues);
    }

    @Override
    public void showLandscapeFragment(int positionOfRouter, int positionOfQueue) {
        mListFlowsByRouterFragmentView.instanciateLandscapeFragment(positionOfRouter,positionOfQueue);
    }

    @Override
    public void showPortraitFragment(int positionOfRouter, int positionOfQueue) {
        mListFlowsByRouterFragmentView.instanciatePortraitFragment(positionOfRouter,positionOfQueue);
    }
}
