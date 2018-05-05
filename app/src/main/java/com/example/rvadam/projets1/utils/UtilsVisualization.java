package com.example.rvadam.projets1.utils;

import android.util.Log;

import com.example.rvadam.projets1.model.Network;
import com.example.rvadam.projets1.model.Queue;
import com.example.rvadam.projets1.model.Router;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rvadam on 18/12/2017.
 */

public class UtilsVisualization {
    private static final String TAG="UTILS";
    public static Router getRouterById(List<Router> listRouters,String id){

        for(Router r: listRouters){
          if(r.getId().equals(id)) {

              return r;
          }
        }

        return null;
    }



    public static Queue getQueueByIdInARouter(Router r,String id){
       if(r!=null) {
           for (Queue q : r.getQueues()) {
               if (q.getId().equals(id)) return q;
           }
       }
        return null;
    }

    public static boolean applyChangesToRouter(Router rtmp) {
        //Only the name of the router can change

        for(Router r: Network.getInstance().getListOfCurrentRouters()){
            if(r.getId().equals(rtmp.getId())){
                if(!(r.getName().equals(rtmp.getName()))){
                    r.setName(rtmp.getName());
                    return true;
                }
            }
        }
        return false;
    }

    public static List<Router> checkRoutersRemovalFromDB(List<Router> alreadyProcessedRouters) {
        List<Router> result=new ArrayList<Router>();
        List<Router> routersInNetworkInstance=Network.getInstance().getListOfCurrentRouters();
        for(Router r: routersInNetworkInstance){
            boolean routerFound=false;
            for(Router r1: alreadyProcessedRouters){
                if(r.getId().equals(r1.getId())){
                    routerFound=true;
                }
            }
            if(!routerFound){

                result.add(r);

            }


        }
        for (Router r: result){
            routersInNetworkInstance.remove(r);
        }
        return result;
    }

    public static boolean applyChangesToQueue(String idrouter, Queue qtmp) {
        Router r=getRouterById(Network.getInstance().getListOfCurrentRouters(),idrouter);
        Queue q=getQueueByIdInARouter(r,qtmp.getId());
        boolean changesMade=false;
        if(q!=null) {
            if (q.getTos() != qtmp.getTos()) {
                q.setTos(qtmp.getTos());
                changesMade = true;
            }
            if (q.getIpsource() != qtmp.getIpsource()) {
                q.setIpsource(qtmp.getIpsource());
                changesMade = true;
            }
            if (q.getIpdestination() != qtmp.getIpdestination()) {
                q.setIpdestination(qtmp.getIpdestination());
                changesMade = true;
            }
            if (q.getPortSource() != qtmp.getPortSource()) {
                q.setPortSource(qtmp.getPortSource());
                changesMade = true;
            }
            if (q.getPortDestination() != qtmp.getPortDestination()) {
                q.setPortDestination(qtmp.getPortDestination());
                changesMade = true;
            }
            if (q.getProtocole() != qtmp.getProtocole()) {
                q.setProtocole(qtmp.getProtocole());
                changesMade = true;
            }
            if (q.getBytecount() != qtmp.getBytecount()) {
                q.setBytecount(qtmp.getBytecount());
                changesMade = true;
            }
            if (q.getCapacity() != qtmp.getCapacity()) {
                q.setCapacity(qtmp.getCapacity());
                changesMade = true;
            }
        }
        return changesMade;


    }

    public static List<Queue> checkQueuesRemovalFromDB(List<Queue> alreadyProcessedQueues) {
        List<Queue> result=new ArrayList<Queue>();
        List<Queue> listofAllQueues=new ArrayList<Queue>();
        for(Router r: Network.getInstance().getListOfCurrentRouters()){
            for(Queue q: r.getQueues()){
                listofAllQueues.add(q);
            }
        }
        for(Queue q: listofAllQueues){
            boolean queueFound=false;
            for (Queue q1:alreadyProcessedQueues){
                if(q.getId().equals(q1.getId())){
                    queueFound=true;
                }
            }
            if(!queueFound){

                Router r=getRouterById(Network.getInstance().getListOfCurrentRouters(),q.getIdrouter());
                if(r!=null) {
                    r.getQueues().remove(q);
                    result.add(q);
                }
            }

        }
           return result;
    }

    public static void addRouter(Router rtmp) {
        Network.getInstance().getListOfCurrentRouters().add(rtmp);
    }

    public static boolean addQueueToRouter(Router r,Queue qtmp) {
        if(r==null || !(Network.getInstance().getListOfCurrentRouters().contains(r))){
            return false;
        }else{
            r.getQueues().add(qtmp);
            return true;
        }

    }

    public static String getIdRouterByPosition(int i){
       return Network.getInstance().getListOfCurrentRouters().get(i).getId();
    }

    public static String getIdQueueByRouterPositionAndQueuePosition(int routerPosition,int queuePosition){
        return getQueueByRouterPositionAndQueuePosition( routerPosition, queuePosition).getId();
    }

    public static String getNameRouterByPosition(int routerPosition){
        return Network.getInstance().getListOfCurrentRouters().get(routerPosition).getName();
    }

    public static Queue getQueueByRouterPositionAndQueuePosition(int routerPosition,int queuePosition){
        return Network.getInstance().getListOfCurrentRouters().get(routerPosition).getQueues().get(queuePosition);
    }



}
