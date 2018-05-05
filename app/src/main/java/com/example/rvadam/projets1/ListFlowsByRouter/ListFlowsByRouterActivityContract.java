package com.example.rvadam.projets1.ListFlowsByRouter;

import com.example.rvadam.projets1.model.Queue;

import java.util.List;

/**
 * Created by rvadam on 06/02/2018.
 */

public interface ListFlowsByRouterActivityContract {

    interface View{
        void showListOfFlows(List<Queue> queuesToDisplay);
        void showListOfRoutersInToolbar(List<String> listNamesOfRouters);

        void showFragmentListOfQueues(int i);
    }
    interface UserActionsListener {

        void updateContentSpinnerToolbar();
       // void loadQueuesCurrent(int positionOfRouter);

        void onChoiceRouter(int i);
    }
}
