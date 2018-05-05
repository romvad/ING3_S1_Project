package com.example.rvadam.projets1.FlowDetails;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.rvadam.projets1.ListFlowsByRouter.ListFlowsByRouterActivity;
import com.example.rvadam.projets1.R;
import com.example.rvadam.projets1.model.Network;
import com.example.rvadam.projets1.model.Queue;
import com.example.rvadam.projets1.model.Router;
import com.example.rvadam.projets1.services.FirebaseService;

import static com.example.rvadam.projets1.utils.UtilsVisualization.getIdQueueByRouterPositionAndQueuePosition;
import static com.example.rvadam.projets1.utils.UtilsVisualization.getIdRouterByPosition;

/**
 * Created by rvadam on 18/12/2017.
 */

public class FlowDetailsActivity extends AppCompatActivity {

    FlowDetailsFragment flowDetailsFragment;
    private static final String TAG = "FlowDetailsActivity";
    int positionSelectedQueue;
    int positionCurrentRouter;
    FragmentManager fm;
    FragmentTransaction ft;
    String currentQueueId;
    String currentRouterId;
    Network network;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_detail);

        network=Network.getInstance();

        //We retrieve the position of the corresponding router and queue to send it to the fragment
        Intent intent = getIntent();
        positionSelectedQueue = intent.getIntExtra(ListFlowsByRouterActivity.POSITION_SELECTED_QUEUE, -1);
        positionCurrentRouter = intent.getIntExtra(ListFlowsByRouterActivity.POSITION_ROUTER_SELECTED, -1);
        Log.d(TAG, "position récupérer queue select " + positionSelectedQueue);
        Log.d(TAG, "position récupérer router select " + positionCurrentRouter);
        Bundle args = new Bundle();
        args.putInt(ListFlowsByRouterActivity.POSITION_SELECTED_QUEUE, positionSelectedQueue);
        args.putInt(ListFlowsByRouterActivity.POSITION_ROUTER_SELECTED, positionCurrentRouter);

        currentQueueId = getIdQueueByRouterPositionAndQueuePosition(positionCurrentRouter,positionSelectedQueue);
        currentRouterId= getIdRouterByPosition(positionCurrentRouter);
        flowDetailsFragment = new FlowDetailsFragment();
        Log.d(TAG, "le bundle " + args.toString());
        flowDetailsFragment.setArguments(args);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //userDetailsFragment=new UserDetailsFragment();
        ft.replace(R.id.detailFlowFragment_container, flowDetailsFragment).commit();


    }
    @Override
    public void onResume() {
        super.onResume();
        // This registers mMessageReceiver to receive messages.
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver,
                        new IntentFilter(FirebaseService.BROADCAST_INTENT_MESSAGE));
    }



    @Override
    protected void onPause() {
        // Unregister since the activity is not visible
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(mMessageReceiver);
        super.onPause();
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract data included in the Intent


            Router currentRouter = network.getListOfCurrentRouters().get(positionCurrentRouter);


            String routerRemoval[] = intent.getStringArrayExtra(FirebaseService.ROUTEUR_REMOVAL);
            String queueRemoval[] = intent.getStringArrayExtra(FirebaseService.QUEUE_REMOVAL);
            String queueCharacteristicChange[] = intent.getStringArrayExtra(FirebaseService.QUEUE_CHARACTERISTIC_CHANGE);
            String updateLoad[] = intent.getStringArrayExtra(FirebaseService.UPDATE_LOAD);

            if ((routerRemoval != null && ( currentRouterId .equals( routerRemoval[0])) || (queueRemoval != null &&  currentQueueId .equals( queueRemoval[1])))) {


                ft = fm.beginTransaction();
                ft.remove(flowDetailsFragment).commit();


                routerRemoval = null;
                queueRemoval = null;
            }

            if ((queueCharacteristicChange != null && queueCharacteristicChange[1] .equals(  currentQueueId) || (updateLoad != null && updateLoad[1] .equals(  currentQueueId)))) {
                flowDetailsFragment.getmActionsListener().updateDetailContent(positionCurrentRouter,positionSelectedQueue);

            }

        }
    };
}
