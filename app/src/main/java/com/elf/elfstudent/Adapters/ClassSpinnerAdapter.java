package com.elf.elfstudent.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.R;
import com.elf.elfstudent.model.StandardModel;

import java.util.List;

/**
 * Created by nandhu on 21/10/16.
 *
 */
public class ClassSpinnerAdapter extends ArrayAdapter<StandardModel>{

    private Context mContext = null;

    private List<StandardModel> mList ;

    public ClassSpinnerAdapter(Context context, int resource, List<StandardModel> objects) {
        super(context, resource, objects);
        this.mContext  = context;
        this.mList = objects;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        if (row == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.simple_spinner,parent,false);
        }
        StandardModel current = mList.get(position);
        if (current != null){
            HelviticaLight mStandarNAme = (HelviticaLight) row.findViewById(R.id.class_name_spinner);
            if (mStandarNAme != null){
                mStandarNAme.setText(current.getName());

            }
        }

        return row;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.simple_spinner,parent,false);
        }
        StandardModel current = mList.get(position);
        if (current != null){
            HelviticaLight mStandarNAme = (HelviticaLight) row.findViewById(R.id.class_name_spinner);
            if (mStandarNAme != null){
                mStandarNAme.setText(current.getName());

            }
        }

        return row;

    }
}
