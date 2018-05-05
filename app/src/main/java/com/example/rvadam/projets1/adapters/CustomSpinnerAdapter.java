package com.example.rvadam.projets1.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.rvadam.projets1.R;
import com.example.rvadam.projets1.model.Queue;
import com.example.rvadam.projets1.model.Router;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by rvadam on 17/12/2017.
 */

public class CustomSpinnerAdapter extends ArrayAdapter<String> implements SpinnerAdapter {
    Context context;
   static List<String> listNamesOfRouters;

    public CustomSpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> listNamesOfRouters) {
        super(context, resource, listNamesOfRouters);
        this.context=context;
        this.listNamesOfRouters=listNamesOfRouters;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //LayoutInflater inflater= ((Activity)context).getLayoutInflater();
        View row;
        if(convertView==null) {

            LayoutInflater inflater = (LayoutInflater.from(context));
            row = inflater.inflate(R.layout.item_spinner, null);
        }else{
            row=convertView;
        }
        TextView nameTextView= (TextView) row.findViewById(R.id.nameRouter);


        /*//We retrieve the queue at the position 'position'
        Router routerAtPosition=listRouters.get(position);*/
        //Log.i(TAG,"position spinner "+routerAtPosition);
        nameTextView.setText(listNamesOfRouters.get(position));

        return row;
    }

}
