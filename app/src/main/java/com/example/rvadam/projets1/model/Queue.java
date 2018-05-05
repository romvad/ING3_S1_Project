package com.example.rvadam.projets1.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rvadam on 12/12/2017.
 */

public class Queue {
   private String id;
    private double portSource, portDestination;
    private double tos;
    private String ipSource,ipDestination;
    private double bytecount;
    private String type;
    private String name;
    private double capacity;
    private Load currentLoad=new Load();
    private String protocole;
    private String idrouter;
    //Map<String,Integer> historyLoad;

    public Queue() {
    }

    public Queue(String id,double portSource, double portDestination, double tos, String ipsource, String ipdestination, double capacity, double bytecount, String type, String name,String protocole,String idrouter) {
        this.id=id;
        this.portSource = portSource;
        this.portDestination = portDestination;
        this.tos = tos;
        this.ipSource = ipsource;
        this.ipDestination = ipdestination;
        this.capacity = capacity;
        this.bytecount = bytecount;
        this.type=type;
        this.name=name;
        this.protocole=protocole;
        this.idrouter=idrouter;
       // this.historyLoad=historyLoad;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public double getPortSource() {
        return portSource;
    }

    public void setPortSource(double portSource) {
        this.portSource = portSource;
    }

    public double getPortDestination() {
        return portDestination;
    }

    public void setPortDestination(double portDestination) {
        this.portDestination = portDestination;
    }

    public double getTos() {
        return tos;
    }

    public void setTos(double tos) {
        this.tos = tos;
    }

    public String getIpsource() {
        return ipSource;
    }

    public void setIpsource(String ipsource) {
        this.ipSource = ipsource;
    }

    public String getIpdestination() {
        return ipDestination;
    }

    public void setIpdestination(String ipdestination) {
        this.ipDestination = ipdestination;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double getBytecount() {
        return bytecount;
    }

    public void setBytecount(double bytecount) {
        this.bytecount = bytecount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProtocole() {
        return protocole;
    }

    public void setProtocole(String protocole) {
        this.protocole = protocole;
    }

    public String getIdrouter() {
        return idrouter;
    }

    public void setIdrouter(String idrouter) {
        this.idrouter = idrouter;
    }

    public Load getCurrentLoad() {
        return currentLoad;
    }

    public void setCurrentLoad(Load currentLoad) {
        this.currentLoad = currentLoad;
    }

}
