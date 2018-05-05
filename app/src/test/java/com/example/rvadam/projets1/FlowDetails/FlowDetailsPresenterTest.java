package com.example.rvadam.projets1.FlowDetails;

import com.example.rvadam.projets1.model.Load;
import com.example.rvadam.projets1.model.Network;
import com.example.rvadam.projets1.model.Queue;
import com.example.rvadam.projets1.model.Router;
import com.example.rvadam.projets1.utils.UtilsVisualization;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * Created by rvadam on 13/02/2018.
 */

public class FlowDetailsPresenterTest {

    private static List<Router> lr=new ArrayList<Router>(); // List of router of Network Singleton on which we will work
    private static Queue q1;
    private static Router r1;

    FlowDetailsPresenter mFlowDetailsPresenter;
    //UtilsVisualization mockUtilsVisualization;
    @Mock
    private FlowDetailsContract.View mFlowDetailsView;

    @Before
    public void prepareTest(){
        lr= Network.getInstance().getListOfCurrentRouters();
        //lr.remove(r1);
        lr.clear();
        q1=new Queue("1",80,100,10,"12.3.4.5", "34.8.6.5",100, 50,"input", "queue1","UDP","1");
        Load q1CurrentLoad=new Load();
        q1CurrentLoad.setCurrentLoad(30);
        q1.setCurrentLoad(q1CurrentLoad);
        List<Queue>lq=new ArrayList<Queue>();
        lq.add(q1);
        r1=new Router("1","router1",lq);
        lr.add(r1);

        MockitoAnnotations.initMocks(this);
        mFlowDetailsPresenter= new FlowDetailsPresenter(mFlowDetailsView);

       PowerMockito.mockStatic(UtilsVisualization.class);
    }
    @Test
    public void testUpdateDetailContent(){
        mFlowDetailsPresenter.updateDetailContent(0,0);
        PowerMockito.verifyStatic(VerificationModeFactory.times(1));
        UtilsVisualization.getQueueByRouterPositionAndQueuePosition(0,0);

        verify(mFlowDetailsView).showQueueInformation(30,80,100,10,"12.3.4.5", "34.8.6.5",100, 50, "input", "queue1","UDP");
    }
}
