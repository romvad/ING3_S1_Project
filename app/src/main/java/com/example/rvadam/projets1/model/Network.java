package com.example.rvadam.projets1.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rvadam on 16/12/2017.
 */

public class Network {
    private static final Network ourInstance = new Network();
    private static List<Router> listOfCurrentRouters=new ArrayList<Router>();;

    public static Network getInstance() {

        return ourInstance;
    }

    private Network(){
       /* Queue q1=new Queue("3",5000,3000,30,"172.20.10.1","134.2.3.4",1000,500,"input","flux 1","UDP");
        Queue q2=new Queue("1",2000,6000,30,"12.20.10.1","14.2.3.4",100,50,"input","flux 2","TCP");
        Queue q3=new Queue("2",7000,3000,30,"172.20.10.1","134.2.3.4",1000,500,"input","flux 11","UDP");
        Queue q4=new Queue("4",8000,1000,30,"172.0.10.1","134.2.3.4",1000,500,"input","flux 12","TCP");
        List<Queue> l1=new ArrayList<Queue>();
        List<Queue> l2=new ArrayList<Queue>();
        l1.add(q1);
        l1.add(q2);
        l2.add(q3);
        l2.add(q4);
        Router r=new Router("1","Routeur 1",l1);
        Router r2= new Router("2","Routeur 2",l2);
        listOfCurrentRouters.add(r);
        listOfCurrentRouters.add(r2);*/
    }

    public List<Router> getListOfCurrentRouters() {
        return listOfCurrentRouters;
    }
}
