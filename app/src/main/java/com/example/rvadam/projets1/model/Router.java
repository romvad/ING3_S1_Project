package com.example.rvadam.projets1.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rvadam on 13/12/2017.
 */

public class Router {
    private String id;
    private String name;
    private  List<Queue> queues=new ArrayList<Queue>();
 //   private int lastUpdate; // timestamp that store the last update on the characteristics of one of the flows linked to the router


    public Router() {
    }

    public Router(String id, String name, List<Queue> listOfQueues) {
        this.id = id;
        this.name=name;
        this.queues=listOfQueues;
    }

    public Router(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  List<Queue> getQueues() {
        return queues;
    }




}
