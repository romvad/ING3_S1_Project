package com.example.rvadam.projets1.ListFlowsByRouter;

import com.example.rvadam.projets1.model.Network;
import com.example.rvadam.projets1.model.Router;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rvadam on 06/02/2018.
 */

public class ListFlowsByRouterActivityPresenter implements ListFlowsByRouterActivityContract.UserActionsListener {

    private final ListFlowsByRouterActivityContract.View mListFlowsByRouterView;

    public ListFlowsByRouterActivityPresenter(ListFlowsByRouterActivityContract.View mListFlowsByRouterView) {
        this.mListFlowsByRouterView = mListFlowsByRouterView;
    }

    @Override
    public void updateContentSpinnerToolbar() {
        List<Router> listOfRouters= Network.getInstance().getListOfCurrentRouters();
        List<String> listNamesOfRouters=new ArrayList<String>();
        for(Router r:listOfRouters){
            listNamesOfRouters.add(r.getName());
        }
        mListFlowsByRouterView.showListOfRoutersInToolbar(listNamesOfRouters);
    }

    /*@Override
    public void loadQueuesCurrent(int positionOfRouter) {

    }*/

    @Override
    public void onChoiceRouter(int i) {
        mListFlowsByRouterView.showFragmentListOfQueues(i);
    }
}
