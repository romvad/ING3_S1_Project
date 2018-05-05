package com.example.rvadam.projets1.FlowDetails;

import com.example.rvadam.projets1.ListFlowsByRouter.ListFlowsByRouterActivityContract;
import com.example.rvadam.projets1.model.Queue;

import static com.example.rvadam.projets1.utils.UtilsVisualization.getQueueByRouterPositionAndQueuePosition;

/**
 * Created by rvadam on 06/02/2018.
 */

public class FlowDetailsPresenter implements FlowDetailsContract.UserActionsListener {

    private final FlowDetailsContract.View mFlowDetailsView;

    public FlowDetailsPresenter(FlowDetailsContract.View mFlowDetailsView) {
        this.mFlowDetailsView = mFlowDetailsView;
    }


    @Override
    public void updateDetailContent(int positionOfRouter,int positionOfQueue) {
        Queue q=getQueueByRouterPositionAndQueuePosition(positionOfRouter,positionOfQueue);

        mFlowDetailsView.showQueueInformation(q.getCurrentLoad().getCurrentLoad(),q.getPortSource(),q.getPortDestination(),q.getTos(),q.getIpsource(), q.getIpdestination(),q.getCapacity(), q.getBytecount(), q.getType(), q.getName(),q.getProtocole());

    }
}
