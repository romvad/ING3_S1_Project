package com.example.rvadam.projets1.model;

/**
 * Created by rvadam on 18/12/2017.
 */

public class Load {
    String idqueue;
    int currentLoad;
    String idrouter;
    String id;

    public Load() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdqueue() {
        return idqueue;
    }

    public void setIdqueue(String idqueue) {
        this.idqueue = idqueue;
    }

    public int getCurrentLoad() {
        return currentLoad;
    }

    public void setCurrentLoad(int currentLoad) {
        this.currentLoad = currentLoad;
    }

    public String getIdrouter() {
        return idrouter;
    }

    public void setIdrouter(String idrouter) {
        this.idrouter = idrouter;
    }
}
