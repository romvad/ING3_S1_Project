package com.example.rvadam.projets1.FlowDetails;

/**
 * Created by rvadam on 06/02/2018.
 */

public interface FlowDetailsContract {

    interface View {
        void showQueueInformation(int currentLoad,double portSource, double portDestination, double tos, String ipsource, String ipdestination, double capacity, double bytecount, String type, String name,String protocole);
    }

    interface UserActionsListener {
        void updateDetailContent(int positionOfRouter,int positionOfQueue);
    }
}
