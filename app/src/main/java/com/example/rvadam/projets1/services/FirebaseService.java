package com.example.rvadam.projets1.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.rvadam.projets1.model.Load;
import com.example.rvadam.projets1.model.Network;
import com.example.rvadam.projets1.model.Queue;
import com.example.rvadam.projets1.model.Router;
import com.example.rvadam.projets1.utils.UtilsVisualization;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by rvadam on 16/12/2017.
 */

public class FirebaseService extends IntentService {

    private static final String TAG="FirebaseService";
    private DatabaseReference refDatabaseRouters;
    private DatabaseReference refDatabaseQueues;
    private DatabaseReference refDatabaseLoads;
    private Network network;
    List<Router> currentRouteurs;

    public static final String ROUTEUR_ADDITION="routeurAddition";
    public static final String ROUTEUR_REMOVAL="routerRemoval";
    public static final String ROUTEUR_CHANGED="routerChanged";
    public static final String QUEUE_ADDITION="queueAddition";
    public static final String QUEUE_REMOVAL="queueRemoval";
    public static final String QUEUE_CHARACTERISTIC_CHANGE="queueCharacteristicChange";
    public static final String UPDATE_LOAD="updateLoad";
    public static final String BROADCAST_INTENT_MESSAGE="activities_notification";

    public FirebaseService( ) {
        super("FirebaseService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        network = Network.getInstance();
        refDatabaseRouters = FirebaseDatabase.getInstance().getReference("routers");
        refDatabaseQueues = FirebaseDatabase.getInstance().getReference("queues");
        refDatabaseLoads = FirebaseDatabase.getInstance().getReference("loads");
        currentRouteurs = network.getListOfCurrentRouters();

        // Reference sur le noeud "routers"
        refDatabaseRouters.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "datasnapshot " + dataSnapshot.toString());
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                //Treatment of the received dataSnapShot for routers node

                processRoutersChanges(dataSnapshot);

                //Reference to the "queues" node of the firebase database
                refDatabaseQueues.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        processQueuesChanges(dataSnapshot);

                        refDatabaseLoads.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                processLoadsChanges(dataSnapshot);

                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value
                                Log.w(TAG, "Failed to read value.", error.toException());
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });






    }





    public void processRoutersChanges(DataSnapshot dataSnapshot) {

        //List<Router> currentRouteurs=network.getListOfCurrentRouters();
        List<Router> alreadyProcessedRouters = new ArrayList<Router>();
        Iterable<DataSnapshot> dataList = dataSnapshot.getChildren();
        Log.i(TAG,"children snaphot router "+dataSnapshot.getChildren().toString());
        Iterator<DataSnapshot> it = dataList.iterator();
        while (it.hasNext()) {
            boolean routerFound = false;
            Router rtmp = it.next().getValue(Router.class);

            Router r = UtilsVisualization.getRouterById(currentRouteurs, rtmp.getId());

            if (r != null) {
                routerFound = true;
                if (UtilsVisualization.applyChangesToRouter(rtmp))
                    sendMessage(ROUTEUR_CHANGED, r.getId(), null);
            }

            if (!routerFound) {
                UtilsVisualization.addRouter(rtmp);//currentRouteurs.add(rtmp);
                sendMessage(ROUTEUR_ADDITION, null, null);
            }
            alreadyProcessedRouters.add(rtmp);
        }
        Log.i(TAG, "currentRouteurs router ref " + currentRouteurs);
        List<Router> routersRemoved = UtilsVisualization.checkRoutersRemovalFromDB(alreadyProcessedRouters);
        for (Router r : routersRemoved) {
           // network.getListOfCurrentRouters().remove(r);
            sendMessage(ROUTEUR_REMOVAL, r.getId(), null);
        }

    }

    public void processQueuesChanges(DataSnapshot dataSnapshot) {

                Log.i(TAG, "datasnapshot" + dataSnapshot.toString());
                //List<Router> currentRouteurs=Network.getInstance().getListOfCurrentRouters();
                Log.i(TAG, "currentRouters " + currentRouteurs);
                List<Queue> alreadyProcessedQueues = new ArrayList<Queue>();
                Log.i(TAG, "dataqueues " + dataSnapshot);
                Iterable<DataSnapshot> dataList = dataSnapshot.getChildren();
                Iterator<DataSnapshot> it = dataList.iterator();
                while (it.hasNext()) {
                    boolean queueFound = false;
                    Queue qtmp = it.next().getValue(Queue.class);

                    Router r = UtilsVisualization.getRouterById(currentRouteurs, qtmp.getIdrouter());
                    Log.i(TAG, "router r " + r);
                    if (r != null) {
                        for (Queue q : r.getQueues()) {
                            if (q.getId().equals(qtmp.getId())) {
                                queueFound = true;
                                //We check if our local queue corresponding to qtmp has received changes
                                if (UtilsVisualization.applyChangesToQueue(r.getId(), qtmp)) {
                                    //Changes happened
                                    sendMessage(QUEUE_CHARACTERISTIC_CHANGE, r.getId(), q.getId()); // means an already known queue has received changes in its characteristic so we have to notify the ListAdapter and, if the landscape mode is activated and the details of the queue are displayed on the right of the list, we have to upadate this fragment
                                }
                            }
                        }


                        if (!queueFound) {
                            UtilsVisualization.addQueueToRouter(r,qtmp);//r.getQueues().add(qtmp);
                            sendMessage(QUEUE_ADDITION, r.getId(), null);// when a queue is added, not necessary to update the detail fragment as for sure, its details are not displayed

                        }
                    }
                    alreadyProcessedQueues.add(qtmp);

                }
                List<Queue> queuesRemoved = UtilsVisualization.checkQueuesRemovalFromDB(alreadyProcessedQueues);
                for (Queue q : queuesRemoved) {

                    sendMessage(QUEUE_REMOVAL, q.getIdrouter(), q.getId());
                }

                Log.i(TAG,"end process Queues");
                Log.i(TAG,"nb de queues "+Network.getInstance().getListOfCurrentRouters().get(0).getQueues().size());
            }

public void processLoadsChanges(DataSnapshot dataSnapshot){
    List<Router> currentRouteurs=Network.getInstance().getListOfCurrentRouters();
    Iterable<DataSnapshot> dataList=dataSnapshot.getChildren();
    Iterator<DataSnapshot> it=dataList.iterator();
                while(it.hasNext()){

        Load ltmp=it.next().getValue(Load.class);
        Router r=UtilsVisualization.getRouterById(currentRouteurs,ltmp.getIdrouter());
        Queue q=UtilsVisualization.getQueueByIdInARouter(r,ltmp.getIdqueue());
        if(q!=null && q.getCurrentLoad().getCurrentLoad()!=ltmp.getCurrentLoad()){
            q.setCurrentLoad(ltmp);
            sendMessage(UPDATE_LOAD,r.getId(),q.getId());
        }

    }

}




            //Function that prepares the message to broadcast to the activities
    private void sendMessage(String messageEvent,String routerId,String queueId) {

        Intent intent = new Intent(BROADCAST_INTENT_MESSAGE);

        String[] tab={routerId,queueId};
        intent.putExtra(messageEvent,tab);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }



}
