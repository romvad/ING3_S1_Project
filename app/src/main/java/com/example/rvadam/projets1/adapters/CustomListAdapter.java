package com.example.rvadam.projets1.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rvadam.projets1.R;
import com.example.rvadam.projets1.model.Queue;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by rvadam on 16/12/2017.
 */

public class CustomListAdapter extends ArrayAdapter<Queue> {

    Context context;
    List<Queue> listQueues;

    public CustomListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Queue> listQueues) {
        super(context, resource, listQueues);
        this.context=context;
        this.listQueues=listQueues;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //LayoutInflater inflater= ((Activity)context).getLayoutInflater();
        View row;
        if(convertView==null) {

            LayoutInflater inflater = (LayoutInflater.from(context));
            row = inflater.inflate(R.layout.item_list_view, null);
        }else{
            row=convertView;
        }
        TextView nameTextView= (TextView) row.findViewById(R.id.nameTextView);
        LinearLayout bytecountMarkerLayout=(LinearLayout) row.findViewById(R.id.bytecountMarker);
        TextView valueBytecountMarker=(TextView) row.findViewById(R.id.valueBytecount);
        TextView valueRightMarker=(TextView) row.findViewById(R.id.valueRight);
        TextView currentLoad=(TextView) row.findViewById(R.id.currentAbsoluteLoad);
        ProgressBar progressBar=(ProgressBar) row.findViewById(R.id.progressBar);


        //We retrieve the queue at the position 'position'

        Queue queueAtPosition=listQueues.get(position);
        int valueOfCapacity=(int) queueAtPosition.getCapacity();
        int valueOfAbsoluteLoad=queueAtPosition.getCurrentLoad().getCurrentLoad();
        int valueOfBytecount=(int)queueAtPosition.getBytecount();
        int valueOfBytecountRate=valueOfBytecount*100/valueOfCapacity;
        String strNameTextView=queueAtPosition.getName()+"("+queueAtPosition.getType()+")";
        nameTextView.setText(strNameTextView);
        String strValueBytecountMarker=context.getResources().getString(R.string.bytecount_indication)+" "+String.valueOf(valueOfBytecount);
        valueBytecountMarker.setText(strValueBytecountMarker);
        valueRightMarker.setText(String.valueOf(valueOfCapacity));
        String strCurrentLoad=context.getResources().getString(R.string.message_current_load)+String.valueOf(valueOfAbsoluteLoad)+" o";
        currentLoad.setText(strCurrentLoad);

        //Determine the position of the bytceount marker
        int widthDPProgressBar=convertFromPxToDp(progressBar.getWidth());
        int horizontalPositionBytecountMarkerLayout=((valueOfBytecount*widthDPProgressBar)/valueOfCapacity)-6; // because the left corner of the arrow is 6dp before
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(convertFromPxToDp(horizontalPositionBytecountMarkerLayout),0,0,0);
        bytecountMarkerLayout.setLayoutParams(lp);

        //Determine the position of the primary progress and the secondary progress of the progressbar
        double valueOfCurrentLoadRate=valueOfAbsoluteLoad*100/valueOfCapacity;
        double valueOfWaitingRate=((valueOfAbsoluteLoad-valueOfBytecount)*100/valueOfCapacity);
        if( valueOfWaitingRate>0){
            progressBar.setSecondaryProgress((int)Math.round(valueOfCurrentLoadRate));
            progressBar.setProgress(Math.round(valueOfBytecountRate));
        }else{
            progressBar.setSecondaryProgress(0);
            progressBar.setProgress((int)Math.round(valueOfCurrentLoadRate));
        }
        return row;
    }

    private int convertFromPxToDp(int px) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));

    }

    public int convertFromDpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));

    }
}
