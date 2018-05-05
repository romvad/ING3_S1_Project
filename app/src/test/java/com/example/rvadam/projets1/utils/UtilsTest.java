package com.example.rvadam.projets1.utils;

import com.example.rvadam.projets1.model.Network;
import com.example.rvadam.projets1.model.Queue;
import com.example.rvadam.projets1.model.Router;
import com.example.rvadam.projets1.utils.UtilsVisualization;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.example.rvadam.projets1.utils.UtilsVisualization.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by rvadam on 05/02/2018.
 */

public class UtilsTest {

    private static List<Router> lr=new ArrayList<Router>(); // List of router of Network Singleton on which we will work
    private static Queue q1;//One of the queue of router r1, that belongs to the Network instance
    private static Queue q2;//One of the queue of router r1, that belongs to the Network instance
    private static Router r1;//one of the router of the Network instance
    private static Router r2;//one of the router of the Network instance

    private static Queue qtmp;//Queue that is supposed to be retrieve from Firebase database (by default, it corresponds to the queue q2) and that is used in the applyChangesToQueue and checkQueuesRemovalFromDB methods tests.

    @Before
    public void prepareModelForTest(){
        lr= Network.getInstance().getListOfCurrentRouters();
        lr.remove(r1);
        lr.remove(r2);

        q1=new Queue("1",80,100,10,"12.3.4.5", "34.8.6.5",100, 50,"input", "queue1","UDP","1");
        q2=new Queue("2",100,1001,110,"13.3.4.5", "35.8.6.5",200, 50,"input", "queue2","TCP","1");
        qtmp=new Queue("2",100,1001,110,"13.3.4.5", "35.8.6.5",200, 50,"input", "queue2","TCP","1");
        List<Queue>lq=new ArrayList<Queue>();
        lq.add(q1);
        lq.add(q2);

        r1=new Router("1","router1",lq);
        r2=new Router("2","router2");
        lr.add(r1);
        lr.add(r2);
    }
    @Test
    public void testGetRouterByExistingId(){

        assertEquals(r2, getRouterById(lr,"2"));


    }

    @Test
    public void testGetRouterByInexistingId(){
        assertEquals(null,getRouterById(lr,"3"));
    }

    @Test
    public void testGetQueueByExisitingIdInAnExistingRouter(){
        assertEquals(q2,getQueueByIdInARouter(r1,"2"));
    }

    @Test
    public void testGetQueueByInexisitingIdInAnExistingRouter(){

        assertEquals(null,getQueueByIdInARouter(r1,"3"));
    }

    @Test
    public void testGetQueueByIdInAnNullRouter(){
        assertEquals(null,getQueueByIdInARouter(null,"2"));
    }

    @Test
    public void testApplyChangesToInexistingRouter(){
        Router rtmp=new Router("3","router3");
        assertFalse(applyChangesToRouter(rtmp));

    }

    @Test
    public void testApplyChangesToExistingRouterWithNameChange(){
        Router rtmp=new Router("2","router3");
        assertTrue(applyChangesToRouter(rtmp));
        assertEquals("router3",r2.getName());

    }

    @Test
    public void testApplyChangesToExistingRouterWithNoChanges(){
        Router rtmp=new Router("2","router2");
        assertFalse(applyChangesToRouter(rtmp));
        assertEquals("router2",r2.getName());

    }

    @Test
    public void testCheckRoutersRemovalFromDBWithOneRemoval(){
        Router rbis1=new Router("1","router1");
        List<Router> lrbis=new ArrayList<Router>();
        lrbis.add(rbis1);

        List<Router> result= checkRoutersRemovalFromDB(lrbis);
        assertEquals(1,result.size());
        assertTrue(result.contains(r2));
        assertFalse(result.contains(r1));
        assertFalse(Network.getInstance().getListOfCurrentRouters().contains(r2));
    }

    @Test
    public void testCheckRoutersRemovalFromDBWithNoRemoval(){
        Router rtmp1=new Router("1","router1");
        Router rtmp2=new Router("2","router2");
        List<Router> lrbis=new ArrayList<Router>();
        lrbis.add(rtmp1);
        lrbis.add(rtmp2);

        assertEquals(0,checkRoutersRemovalFromDB(lrbis).size());
        assertTrue(Network.getInstance().getListOfCurrentRouters().contains(r2));
        assertTrue(Network.getInstance().getListOfCurrentRouters().contains(r1));
    }

    @Test
    public void testApplyChangesToAnInexistingQueue(){
        qtmp.setId("3");
        assertFalse(applyChangesToQueue("1",qtmp));
    }

    @Test
    public void testApplyChangesToAnExistingQueueWithNoChanges(){
        assertFalse(applyChangesToQueue("1",qtmp));
    }

    @Test
    public void testApplyChangesToAnExistingQueueWithTOSChange(){
        qtmp.setTos(120);
        assertTrue(applyChangesToQueue("1",qtmp));
        assertTrue(q2.getTos()==120);
    }

    @Test
    public void testApplyChangesToAnExistingQueueWithIPSourceChange(){
        qtmp.setIpsource("1.1.1.1");
        assertTrue(applyChangesToQueue("1",qtmp));
        assertTrue(q2.getIpsource()=="1.1.1.1");
    }

    @Test
    public void testApplyChangesToAnExistingQueueWithIPDestinationChange(){
        qtmp.setIpdestination("1.1.1.1");
        assertTrue(applyChangesToQueue("1",qtmp));
        assertTrue(q2.getIpdestination()=="1.1.1.1");
    }

    @Test
    public void testApplyChangesToAnExistingQueueWithPortSourceChange(){
        qtmp.setPortSource(99);
        assertTrue(applyChangesToQueue("1",qtmp));
        assertTrue(q2.getPortSource()==99);
    }

    @Test
    public void testApplyChangesToAnExistingQueueWithPortDestinationChange(){
        qtmp.setPortDestination(99);
        assertTrue(applyChangesToQueue("1",qtmp));
        assertTrue(q2.getPortDestination()==99);
    }

    @Test
    public void testApplyChangesToAnExistingQueueWithProtocoleChange(){
        qtmp.setProtocole("POIU");
        assertTrue(applyChangesToQueue("1",qtmp));
        assertTrue(q2.getProtocole()=="POIU");
    }

    @Test
    public void testApplyChangesToAnExistingQueueWithBytecountChange(){
        qtmp.setBytecount(99);
        assertTrue(applyChangesToQueue("1",qtmp));
        assertTrue(q2.getBytecount()==99);
    }

    @Test
    public void testApplyChangesToAnExistingQueueWithCapacityChange(){
        qtmp.setCapacity(99);
        assertTrue(applyChangesToQueue("1",qtmp));
        assertTrue(q2.getCapacity()==99);
    }

    @Test
    public void testCheckQueuesRemovalFromDBWithOneRemoval(){
        List<Queue> lq=new ArrayList<Queue>();
        lq.add(qtmp);

        List<Queue> result= checkQueuesRemovalFromDB(lq);

        assertEquals(1,result.size());
        assertTrue(result.contains(q1));
        //assertFalse(result.contains(q2));
        assertFalse(Network.getInstance().getListOfCurrentRouters().get(0).getQueues().contains(q1));
        assertTrue(Network.getInstance().getListOfCurrentRouters().get(0).getQueues().contains(q2));

    }

    @Test
    public void testCheckQueuesRemovalFromDBWithNoRemoval(){
        Queue qtmp2=new Queue("1",80,100,10,"12.3.4.5", "34.8.6.5",100, 50,"input", "queue1","UDP","1");
        List<Queue> lq=new ArrayList<Queue>();
        lq.add(qtmp);
        lq.add(qtmp2);

        assertEquals(0,checkQueuesRemovalFromDB(lq).size());
        assertTrue(Network.getInstance().getListOfCurrentRouters().get(0).getQueues().contains(q1));
        assertTrue(Network.getInstance().getListOfCurrentRouters().get(0).getQueues().contains(q2));
    }

    @Test
    public void testAddQueueWithInexistingRouter(){
        Router r=new Router("3","router3");
        Queue q=new Queue("3",80,100,10,"12.3.4.5", "34.8.6.5",100, 50,"input", "queue1","UDP","3");

        assertFalse(addQueueToRouter(r,q));
        assertFalse(Network.getInstance().getListOfCurrentRouters().get(0).getQueues().contains(q));
        assertFalse(Network.getInstance().getListOfCurrentRouters().get(1).getQueues().contains(q));
    }

    @Test
    public void testAddQueueWithNullRouter(){
        Router r=null;
        Queue q=new Queue("3",80,100,10,"12.3.4.5", "34.8.6.5",100, 50,"input", "queue1","UDP","3");

        assertFalse(addQueueToRouter(r,q));
        assertFalse(Network.getInstance().getListOfCurrentRouters().get(0).getQueues().contains(q));
        assertFalse(Network.getInstance().getListOfCurrentRouters().get(1).getQueues().contains(q));
    }

    @Test
    public void testAddQueue(){
        Queue q=new Queue("3",80,100,10,"12.3.4.5", "34.8.6.5",100, 50,"input", "queue3","UDP","2");

        assertTrue(addQueueToRouter(r2,q));
        assertFalse(Network.getInstance().getListOfCurrentRouters().get(0).getQueues().contains(q));
        assertTrue(Network.getInstance().getListOfCurrentRouters().get(1).getQueues().contains(q));
    }

    @Test
    public void testAddRouter(){
        Router r=new Router("3","router3");
        addRouter(r);
        assertTrue(Network.getInstance().getListOfCurrentRouters().contains(r));
    }

}
