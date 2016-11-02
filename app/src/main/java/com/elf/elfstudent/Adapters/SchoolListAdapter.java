package com.elf.elfstudent.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.R;
import com.elf.elfstudent.model.InstitutionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 17/10/16.
 *
 *
 */
public class SchoolListAdapter extends ArrayAdapter<InstitutionModel> {



    private LayoutInflater inflater = null;

    private Context mContext = null;
    private List<InstitutionModel> mList = null;

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null){
            inflater = LayoutInflater.from(mContext);
        }
        if (convertView == null){

            //The Root View if Adapter
            convertView  = inflater.inflate(R.layout.institution_item_row_new,parent,false);

            //T
        }
        HelviticaLight mInsName = (HelviticaLight) convertView.findViewById(R.id.ins_name_new);

        mInsName.setText(mList.get(position).getInsName());
        return convertView;
    }

    @Nullable
    @Override
    public InstitutionModel getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (inflater == null){
            inflater = LayoutInflater.from(mContext);
        }
        if (convertView == null){

            //The Root View if Adapter
            convertView  = inflater.inflate(R.layout.institution_item_row_new,parent,false);

            //T
        }
        HelviticaLight mInsName = (HelviticaLight) convertView.findViewById(R.id.ins_name_new);

        mInsName.setText(mList.get(position).getInsName());
        return convertView;
    }


    @Override
    public int getCount() {
        return mList.size();

    }

    public SchoolListAdapter(Context context, int resource, List<InstitutionModel> objects) {
        super(context, resource, objects);
        this.mContext  = context;
        this.mList = objects;


    }

}