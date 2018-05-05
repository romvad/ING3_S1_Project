package com.example.rvadam.projets1.ListFlowsByRouter;

import com.example.rvadam.projets1.model.Queue;

import java.util.List;

/**
 * Created by rvadam on 06/02/2018.
 */

public interface ListFlowsByRouterFragmentContract {

    interface View{
        void showListOfFlows(List<Queue> queuesToDisplay);
        void instanciateLandscapeFragment(int positionOfRouter,int positionOfQueue);

        void instanciatePortraitFragment(int positionOfRouter, int positionOfQueue);

        //void showFragmentListOfQueues(int i);
    }
    interface UserActionsListener {


        void loadQueuesCurrent(int positionOfRouter);
        void showLandscapeFragment(int positionOfRouter,int positionOfQueue);


        void showPortraitFragment(int positionRouterSelected, int position);
    }
}
