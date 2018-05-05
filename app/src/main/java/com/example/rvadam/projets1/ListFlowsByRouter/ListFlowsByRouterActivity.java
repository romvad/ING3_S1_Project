package com.example.rvadam.projets1.ListFlowsByRouter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.rvadam.projets1.FlowDetails.FlowDetailsFragment;
import com.example.rvadam.projets1.ListFlowsByRouter.ListFlowsByRouterActivityContract;
import com.example.rvadam.projets1.ListFlowsByRouter.ListFlowsByRouterActivityPresenter;
import com.example.rvadam.projets1.R;
import com.example.rvadam.projets1.adapters.CustomSpinnerAdapter;
import com.example.rvadam.projets1.model.Queue;
import com.example.rvadam.projets1.model.Router;
import com.example.rvadam.projets1.services.FirebaseService;

import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.rvadam.projets1.utils.UtilsVisualization.getIdQueueByRouterPositionAndQueuePosition;
import static com.example.rvadam.projets1.utils.UtilsVisualization.getIdRouterByPosition;

/**
 * Created by rvadam on 16/12/2017.
 */

public class ListFlowsByRouterActivity extends AppCompatActivity implements ListFlowsByRouterActivityContract.View{

    List<Router> listOfRouters;
    FragmentManager fm;
    FragmentTransaction ft;
    boolean mDualPane;
    ListFlowsByRouterFragment listFlowsByRouterFragment;
    Spinner spinnerToolbar;
    Router currentRouteur;
    CustomSpinnerAdapter adapter;
    int positionCurrentRouter;
    //static  List<String>listNamesOfRouters;
    private ListFlowsByRouterActivityContract.UserActionsListener mActionsListener;


    public static final String POSITION_ROUTER_SELECTED = "positionRouterSelected";
    public static final String POSITION_SELECTED_QUEUE="positionSelectedQueue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_flow_and_or_detail_flow);
        mActionsListener=new ListFlowsByRouterActivityPresenter(this);

        View detailFrame = findViewById(R.id.detail_container);
        mDualPane = detailFrame != null && detailFrame.getVisibility() == View.VISIBLE;

        fm = getFragmentManager();
        //Setting of the toolbar for edition/suppression
        //listOfRouters=Network.getInstance().getListOfCurrentRouters();
        Toolbar toolbar;
        toolbar =  (Toolbar) findViewById(R.id.flow_visualization_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.logo_qos);

        spinnerToolbar=(Spinner) findViewById(R.id.spinner);
        mActionsListener.updateContentSpinnerToolbar();


        //Wwe prepare the list of string for routers' name
        /*listNamesOfRouters=new ArrayList<String>();
        for(Router r:listOfRouters){
            listNamesOfRouters.add(r.getName());
        }
        adapter=new CustomSpinnerAdapter(this,R.layout.item_spinner,listNamesOfRouters);
        //adapter=new CustomSpinnerAdapter(this,R.layout.item_spinner,listOfRouters);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerToolbar.setAdapter(adapter);*/


        //At the activity creation, we pre select the first router of the list in the Network singleton
        spinnerToolbar.setSelection(0);

        spinnerToolbar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // instanciation of the list view fragment

                mActionsListener.onChoiceRouter(i);
                /*listFlowsByRouterFragment=new ListFlowsByRouterFragment();
                Bundle args=new Bundle();
                args.putInt(POSITION_ROUTER_SELECTED,i);
                listFlowsByRouterFragment.setArguments(args);
                ft=fm.beginTransaction();
                ft.replace(R.id.listFragment_container,listFlowsByRouterFragment).commit();

                //We check if, in landscape mode, a FlowDetailsFragment is display: if it is the case, we have to remove it since we change the router
               Fragment currentFragmentRight= fm.findFragmentById(R.id.detail_container);
               Log.i(TAG,"cur "+currentFragmentRight);
               Log.i(TAG,"list "+listOfRouters);
               Log.i(TAG,"cur r "+currentRouteur);
               if(currentRouteur!=null && mDualPane &&!(listOfRouters.get(i).getId().equals(currentRouteur.getId()))&& currentFragmentRight !=null){
                   Log.i(TAG,"ENTREE DANS BOUCLE");
                   ft=fm.beginTransaction();
                   ft.remove(currentFragmentRight).commit();
               }
                currentRouteur=listOfRouters.get(i);*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });

        //We retrieve the list of routers
        // listOfRouters=Network.getInstance().getListOfCurrentRouters();

       /* fm=getFragmentManager();
        ft=fm.beginTransaction();
        if(fm.findFragmentById(R.id.detail_container)!=null)  Log.d(TAG," contenereur de droite "+fm.findFragmentById(R.id.detail_container).toString());

        listFlowsByRouterFragment=new ListFlowsByRouterFragment();
        Bundle args=new Bundle();
        args.putInt(POSITION_ROUTER_SELECTED,0);
        listFlowsByRouterFragment.setArguments(args);
        ft.replace(R.id.listFragment_container,listFlowsByRouterFragment).commit();  ESSAI */
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.visualization_flows_toolbar, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        CustomSpinnerAdapter adapter=new CustomSpinnerAdapter(this,R.layout.item_spinner,listOfRouters);
        /*String[] listNames={};
        int i=0;
        for (Router r: listOfRouters){
            listNames[i]=r.getName();
            i++;
        }

        ArrayAdapter<String> adapter = ArrayAdapter.createFromResource(this,
               listNames, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/

       /* spinner.setAdapter(adapter);
        return true;
    }*/

    //Reception of the broadcast messages sent by the FirebaseService
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            FlowDetailsFragment currentRigthFragment=null;
            int positionQueueInRightFragment=-1;
            String idQueueInRightFragment="inexistant";

            //We get the extra of the broadcast messages: we try to get all the possible, even if there is one message at each call
            //index 0 contains the id of the router concerned by a modification that lead to the message
            //index 1 centains the id of the router concerned by a modification that lead to the message
            String routeurAddition[]= intent.getStringArrayExtra(FirebaseService.ROUTEUR_ADDITION);
            String routerRemoval[]=intent.getStringArrayExtra(FirebaseService.ROUTEUR_REMOVAL);
            String queueAddition[]=intent.getStringArrayExtra(FirebaseService.QUEUE_ADDITION);
            String queueRemoval[]=intent.getStringArrayExtra(FirebaseService.QUEUE_REMOVAL);
            String queueCharacteristicChange[]=intent.getStringArrayExtra(FirebaseService.QUEUE_CHARACTERISTIC_CHANGE);
            String updateLoad[]=intent.getStringArrayExtra(FirebaseService.UPDATE_LOAD);

            Fragment fragToTest=fm.findFragmentById(R.id.detail_container);
            if(mDualPane&& fragToTest instanceof FlowDetailsFragment){//We get the right side fragment if it exists
                currentRigthFragment=(FlowDetailsFragment) fm.findFragmentById(R.id.detail_container);
                positionQueueInRightFragment=currentRigthFragment.getArguments().getInt(POSITION_SELECTED_QUEUE);
                idQueueInRightFragment= getIdQueueByRouterPositionAndQueuePosition(positionCurrentRouter,positionQueueInRightFragment);        }

            if(routeurAddition!=null){
                //Update of the router list doesn't work
                adapter.notifyDataSetChanged();
                routeurAddition=null;
            }

            if(routerRemoval!=null){
                //Update of the router list doesn't work
                adapter.notifyDataSetChanged();
                if(getIdRouterByPosition(positionCurrentRouter).equals(routerRemoval[0])){// if the current visualized router is removed, we remove the list of FA from the screen
                    ft=fm.beginTransaction();
                    ft.remove(listFlowsByRouterFragment).commit();

                    if(mDualPane&& currentRigthFragment !=null){//if(currentRigthFragment!=null){

                        ft=fm.beginTransaction();
                        ft.remove(currentRigthFragment).commit();

                    }
                }
                routerRemoval=null;
            }
            if(queueRemoval!=null &&getIdRouterByPosition(positionCurrentRouter).equals(queueRemoval[0])){//Check if the id of the router where the queue has been deleted is equal to the id of the current visualized router
                listFlowsByRouterFragment.getAdapter().notifyDataSetChanged(); //Update of the displayed list of queue (remove the deleted queue from the screen
                if(mDualPane &&idQueueInRightFragment.equals(queueRemoval[1])){// Check if landscape mode and if the current details on the right concern the deleted queue
                    ft=fm.beginTransaction();
                    ft.remove(currentRigthFragment).commit();
                }
                queueRemoval=null;
            }
            if(queueAddition!=null &&getIdRouterByPosition(positionCurrentRouter).equals(queueAddition[0]) ){// refresh the listview when a queue is added to the current visualized router
                listFlowsByRouterFragment.getAdapter().notifyDataSetChanged();
                queueAddition=null;
            }

            if((queueCharacteristicChange!=null && queueCharacteristicChange[0].equals(getIdRouterByPosition(positionCurrentRouter)))){ //if a queue of the current visualized router has its characteristic changes
                listFlowsByRouterFragment.getAdapter().notifyDataSetChanged();

                if(mDualPane &&(idQueueInRightFragment.equals(queueCharacteristicChange[1]))&&currentRigthFragment!=null){// furthermore, if landscape mode and the visualized queue in the right fragment is the modified queue
                    currentRigthFragment.getmActionsListener().updateDetailContent(positionCurrentRouter,positionQueueInRightFragment);
                }
            }

            if((updateLoad!=null&& updateLoad[0].equals(getIdRouterByPosition(positionCurrentRouter)))){// refresh of the list of queue when the current load of one of the queue is changed
                listFlowsByRouterFragment.getAdapter().notifyDataSetChanged();

                if(mDualPane &&( idQueueInRightFragment.equals(updateLoad[1])) &&currentRigthFragment!=null){
                    currentRigthFragment.getmActionsListener().updateDetailContent(positionCurrentRouter,positionQueueInRightFragment);
                }
            }

        }
    };

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

    public boolean ismDualPane() {
        return mDualPane;
    }

    @Override
    public void showListOfFlows(List<Queue> queuesToDisplay) {

    }

    @Override
    public void showListOfRoutersInToolbar(List<String> listNamesOfRouters) {
        adapter=new CustomSpinnerAdapter(this,R.layout.item_spinner,listNamesOfRouters);
        //adapter=new CustomSpinnerAdapter(this,R.layout.item_spinner,listOfRouters);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerToolbar.setAdapter(adapter);
    }

    @Override
    public void showFragmentListOfQueues(int i) {
        listFlowsByRouterFragment=new ListFlowsByRouterFragment();
        Bundle args=new Bundle();
        args.putInt(POSITION_ROUTER_SELECTED,i);
        listFlowsByRouterFragment.setArguments(args);
        ft=fm.beginTransaction();
        ft.replace(R.id.listFragment_container,listFlowsByRouterFragment).commit();

        //We check if, in landscape mode, a FlowDetailsFragment is display: if it is the case, we have to remove it since we change the router
        Fragment currentFragmentRight= fm.findFragmentById(R.id.detail_container);
        Log.i(TAG,"cur "+currentFragmentRight);
        Log.i(TAG,"list "+listOfRouters);
        //Log.i(TAG,"cur r "+currentRouteur);
        if(getIdRouterByPosition(positionCurrentRouter)!=null && mDualPane &&!(getIdRouterByPosition(i).equals(getIdRouterByPosition(positionCurrentRouter)))&& currentFragmentRight !=null){
            Log.i(TAG,"ENTREE DANS BOUCLE");
            ft=fm.beginTransaction();
            ft.remove(currentFragmentRight).commit();
        }
        positionCurrentRouter=i;
    }

    public BroadcastReceiver getmMessageReceiver() {
        return mMessageReceiver;
    }
}
