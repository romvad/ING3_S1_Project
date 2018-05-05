package com.example.rvadam.projets1.FlowDetails;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rvadam.projets1.FlowDetails.FlowDetailsContract;
import com.example.rvadam.projets1.FlowDetails.FlowDetailsPresenter;
import com.example.rvadam.projets1.ListFlowsByRouter.ListFlowsByRouterActivity;
import com.example.rvadam.projets1.R;
import com.example.rvadam.projets1.model.Network;
import com.example.rvadam.projets1.model.Queue;

/**
 * Created by rvadam on 18/12/2017.
 */

public class FlowDetailsFragment extends Fragment implements FlowDetailsContract.View {
    TextView nameCurrentQueue;
    TextView currentWaitingRate;
    TextView currentLoad;
    ProgressBar circularProgressbar;
    TextView currentBytecount;
    TextView currentTOS;
    TextView currentCapacity;
    TextView currentPortDestination;
    TextView currentPortSource;
    TextView currentIpDestination;
    TextView currentIpSource;
    TextView currentProtocol;
    Network network;
    int positionSelectedQueue;
    int positionCurrentRouter;
    Queue currentQueue;

    FlowDetailsContract.UserActionsListener mActionsListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        //setHasOptionsMenu(true);

        Bundle bundle = getArguments();
        positionSelectedQueue = bundle.getInt(ListFlowsByRouterActivity.POSITION_SELECTED_QUEUE, -1);
        positionCurrentRouter= bundle.getInt(ListFlowsByRouterActivity.POSITION_ROUTER_SELECTED,-1);

        mActionsListener=new FlowDetailsPresenter(this);
        // final AppCompatActivity activity = (AppCompatActivity) getActivity();
        View view = inflater.inflate(R.layout.flow_detail_fragment, container, false);

        return view;

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof ListFlowsByRouterActivity) {
            ListFlowsByRouterActivity currentActivity = (ListFlowsByRouterActivity) getActivity();

            if (!currentActivity.ismDualPane()) return;
        }

        nameCurrentQueue = (TextView) getActivity().findViewById(R.id.nameCurrentQueue);
        currentWaitingRate = (TextView) getActivity().findViewById(R.id.currentWaitingRate);
        currentLoad = (TextView) getActivity().findViewById(R.id.currentLoad);
        circularProgressbar=(ProgressBar) getActivity().findViewById(R.id.circularProgressbar) ;
        currentBytecount = (TextView) getActivity().findViewById(R.id.currentBytecount);
        currentTOS = (TextView) getActivity().findViewById(R.id.currentTOS);
        currentCapacity = (TextView) getActivity().findViewById(R.id.currentCapacity);
        currentPortDestination = (TextView) getActivity().findViewById(R.id.currentPortDestination);
        currentPortSource = (TextView) getActivity().findViewById(R.id.currentPortSource);
        currentIpDestination = (TextView) getActivity().findViewById(R.id.currentIpDestination);
        currentIpSource = (TextView) getActivity().findViewById(R.id.currentIpSource);
        currentProtocol = (TextView) getActivity().findViewById(R.id.currentProtocol);


        currentQueue=Network.getInstance().getListOfCurrentRouters().get(positionCurrentRouter).getQueues().get(positionSelectedQueue);

        mActionsListener.updateDetailContent(positionCurrentRouter,positionSelectedQueue);
    }

   /* public void updateDetailContent(){
        nameCurrentQueue.setText(currentQueue.getName());
        int valueOfCapacity=(int)currentQueue.getCapacity();
        int valueOfAbsoluteLoad=currentQueue.getCurrentLoad().getCurrentLoad();
         int valueOfBytecount=(int)currentQueue.getBytecount();
         int valueOfBytecountRate=valueOfBytecount/valueOfCapacity;
        double valueOfWaitingRate=((valueOfAbsoluteLoad-valueOfBytecount)*100/valueOfCapacity);
        double valueOfCurrentLoadRate=valueOfAbsoluteLoad*100/valueOfCapacity;

        //       Update of the circularProgressbar : the secondaryProgress (orange) Attribute is used for the totla load whereas the
        //  primaryProgress (green) attribute is used to show the total load of it is not higher than the bytecount or the rate of the bytecount if
        // the load is higher  --> green part of the progressbar show the packet that exit the queue at the first round, the other are represented
        // with the orange part

        String strCurrentWaitingRate=getResources().getString(R.string.waiting_size_packet_label);
        if( valueOfWaitingRate>0){
            strCurrentWaitingRate+=(int)valueOfWaitingRate+"%";
            currentWaitingRate.setText(strCurrentWaitingRate);
            circularProgressbar.setSecondaryProgress((int)Math.round(valueOfCurrentLoadRate));
            circularProgressbar.setProgress(Math.round(valueOfBytecountRate));
        }else{
            strCurrentWaitingRate+="0%";
            currentWaitingRate.setText(strCurrentWaitingRate);
            circularProgressbar.setSecondaryProgress(0);
            circularProgressbar.setProgress((int)Math.round(valueOfCurrentLoadRate));
        }
String strCurrentLoad=(int) valueOfCurrentLoadRate+"%";
        currentLoad.setText(strCurrentLoad);

        currentBytecount.setText(String.valueOf(valueOfBytecount));
        currentTOS.setText(String.valueOf((int)currentQueue.getTos()));
        currentCapacity.setText(String.valueOf(valueOfCapacity));
        currentPortDestination.setText(String.valueOf((int)currentQueue.getPortDestination()));
        currentPortSource.setText(String.valueOf((int)currentQueue.getPortSource()));
        currentIpDestination.setText(currentQueue.getIpdestination());
        currentIpSource.setText(currentQueue.getIpsource());
        currentProtocol.setText(currentQueue.getProtocole());

    }*/

    @Override
    public void showQueueInformation(int currentLoadNumber, double portSource, double portDestination, double tos, String ipsource, String ipdestination, double capacity, double bytecount, String type, String name, String protocole) {
        nameCurrentQueue.setText(name);
        int valueOfCapacity=(int)capacity;
        int valueOfAbsoluteLoad=currentLoadNumber;
        int valueOfBytecount=(int)bytecount;
        int valueOfBytecountRate=valueOfBytecount/valueOfCapacity;
        double valueOfWaitingRate=((valueOfAbsoluteLoad-valueOfBytecount)*100/valueOfCapacity);
        double valueOfCurrentLoadRate=valueOfAbsoluteLoad*100/valueOfCapacity;

        //       Update of the circularProgressbar : the secondaryProgress (orange) Attribute is used for the totla load whereas the
        //  primaryProgress (green) attribute is used to show the total load of it is not higher than the bytecount or the rate of the bytecount if
        // the load is higher  --> green part of the progressbar show the packet that exit the queue at the first round, the other are represented
        // with the orange part

        String strCurrentWaitingRate=getResources().getString(R.string.waiting_size_packet_label);
        if( valueOfWaitingRate>0){
            strCurrentWaitingRate+=(int)valueOfWaitingRate+"%";
            currentWaitingRate.setText(strCurrentWaitingRate);
            circularProgressbar.setSecondaryProgress((int)Math.round(valueOfCurrentLoadRate));
            circularProgressbar.setProgress(Math.round(valueOfBytecountRate));
        }else{
            strCurrentWaitingRate+="0%";
            currentWaitingRate.setText(strCurrentWaitingRate);
            circularProgressbar.setSecondaryProgress(0);
            circularProgressbar.setProgress((int)Math.round(valueOfCurrentLoadRate));
        }
        String strCurrentLoad=(int) valueOfCurrentLoadRate+"%";
        currentLoad.setText(strCurrentLoad);

        currentBytecount.setText(String.valueOf(valueOfBytecount));
        currentTOS.setText(String.valueOf((int)tos));
        currentCapacity.setText(String.valueOf(valueOfCapacity));
        currentPortDestination.setText(String.valueOf((int)portDestination));
        currentPortSource.setText(String.valueOf((int)portSource));
        currentIpDestination.setText(ipdestination);
        currentIpSource.setText(ipsource);
        currentProtocol.setText(protocole);
    }

    public FlowDetailsContract.UserActionsListener getmActionsListener() {
        return mActionsListener;
    }
}
