package com.example.rvadam.projets1.ListFlowsByRouter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rvadam.projets1.FlowDetails.FlowDetailsActivity;
import com.example.rvadam.projets1.FlowDetails.FlowDetailsFragment;
import com.example.rvadam.projets1.ListFlowsByRouter.ListFlowsByRouterFragmentContract;
import com.example.rvadam.projets1.ListFlowsByRouter.ListFlowsByRouterFragmentPresenter;
import com.example.rvadam.projets1.R;
import com.example.rvadam.projets1.adapters.CustomListAdapter;
import com.example.rvadam.projets1.model.Network;
import com.example.rvadam.projets1.model.Queue;
import com.example.rvadam.projets1.model.Router;

import java.util.List;

import static com.example.rvadam.projets1.utils.UtilsVisualization.getNameRouterByPosition;

/**
 * Created by rvadam on 16/12/2017.
 */

public class ListFlowsByRouterFragment  extends ListFragment implements ListFlowsByRouterFragmentContract.View {

    //Network network;
    //List<Router> listRouters;
    CustomListAdapter adapter;
    private static final String TAG = "ListPersonsFragment";
    int positionRouterSelected;

    private ListFlowsByRouterFragmentContract.UserActionsListener mActionsListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        // network=Network.getInstance();
        //listRouters=network.getListOfCurrentRouters();
        //setHasOptionsMenu(true);

        Bundle bundle = getArguments();
        positionRouterSelected = bundle.getInt("positionRouterSelected", -1);

        mActionsListener = new ListFlowsByRouterFragmentPresenter(this);
        // final AppCompatActivity activity = (AppCompatActivity) getActivity();
        View view = inflater.inflate(R.layout.list_queues_fragment, container, false);




        /*setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent callUserDetails = new Intent(getActivity(), UserDetailsActivity.class);
                Person selectedPerson = listPersons.get(position);
                callUserDetails.putExtra("selectedPerson", selectedPerson);
                startActivity(callUserDetails);
            }
        });*/

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Log.i(TAG,"nombre de queues select"+ listRouters.size());
        //adapter=new CustomListAdapter(getActivity(),R.layout.item_list_view,listRouters.get(positionRouterSelected).getQueues());
        //setListAdapter(adapter);

        mActionsListener.loadQueuesCurrent(positionRouterSelected);

        //Display of the name of the selected router
        TextView nameRouter = (TextView) getActivity().findViewById(R.id.nameCurrentRouter);
        String strNameRouter = getResources().getString(R.string.name_router_label) + getNameRouterByPosition(positionRouterSelected);
        nameRouter.setText(strNameRouter);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //super.onListItemClick(l, v, position, id);
        //if (getActivity() instanceof ListFlowsByRouterActivity) {
        ListFlowsByRouterActivity currentActivity = (ListFlowsByRouterActivity) getActivity();
        if (currentActivity.ismDualPane()) {
            mActionsListener.showLandscapeFragment(positionRouterSelected, position);
            //}

            //}
        } else {// portait mode

            mActionsListener.showPortraitFragment(positionRouterSelected, position);
        }

    }


    public void warningSelection() {
        Toast.makeText(getActivity().getApplicationContext(), "Veuillez sélectionner une personne de cette liste SVP.", Toast.LENGTH_LONG).show();
    }

    public CustomListAdapter getAdapter() {
        return adapter;
    }

    public void showListOfFlows(List<Queue> queuesToDisplay) {
        adapter = new CustomListAdapter(getActivity(), R.layout.item_list_view, queuesToDisplay);
        setListAdapter(adapter);
    }

    @Override
    public void instanciateLandscapeFragment(int positionOfRouter, int positionOfQueue) {
        ListFlowsByRouterActivity currentActivity = (ListFlowsByRouterActivity) getActivity();
        if (currentActivity.ismDualPane()) {
            FragmentManager fm = currentActivity.getFragmentManager();
            Log.d(TAG, "juste avant ajout addition de fragment de droite");
            Fragment currentRightFragment = fm.findFragmentById(R.id.detail_container);
            //if(currentActivity.ismDualPane()){

            //Useless to refresh the right fragment when we select the same queue than the current one
            if (!(currentRightFragment instanceof FlowDetailsFragment) || currentRightFragment.getArguments().getInt(ListFlowsByRouterActivity.POSITION_SELECTED_QUEUE) != positionOfQueue) {
                Log.d(TAG, "Instanciation fragment de droite");
                if (fm.findFragmentById(R.id.detail_container) != null)
                    Log.d(TAG, "LE FRAGMENT est:" + fm.findFragmentById(R.id.detail_container).toString());
                FlowDetailsFragment newFlowDetailsFragment = new FlowDetailsFragment();
                Bundle args = new Bundle();
                args.putInt(ListFlowsByRouterActivity.POSITION_SELECTED_QUEUE, positionOfQueue);
                args.putInt(ListFlowsByRouterActivity.POSITION_ROUTER_SELECTED, positionOfRouter);
                newFlowDetailsFragment.setArguments(args);
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.detail_container, newFlowDetailsFragment).commit();
                if (fm.findFragmentById(R.id.detail_container) != null)
                    Log.d(TAG, "LE FRAGMENT est (bis):" + fm.findFragmentById(R.id.detail_container).toString());
            }
        }


    }

    @Override
    public void instanciatePortraitFragment(int positionOfRouter, int positionOfQueue) {
        Intent callUserDetails = new Intent(getActivity(), FlowDetailsActivity.class);
        //callUserDetails.putExtra("PositionSelectedPerson",position);
        Bundle args = new Bundle();
        args.putInt(ListFlowsByRouterActivity.POSITION_SELECTED_QUEUE, positionOfQueue);
        args.putInt(ListFlowsByRouterActivity.POSITION_ROUTER_SELECTED, positionOfRouter);
        callUserDetails.putExtras(args);
        //Log.d(TAG,"position de la queue sélectionnée "+position);
        startActivity(callUserDetails);
    }
}
