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
 */
public class ClassSpinnerAdapter extends ArrayAdapter<StandardModel>{

    private Context mContext = null;
    private LayoutInflater inflater  = null;
    private List<StandardModel> mList = null;

    public ClassSpinnerAdapter(Context context, int resource, List<StandardModel> objects) {
        super(context, resource, objects);
        this.mContext  = context;
        this.mList = objects;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return super.getView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        if (inflater == null) {
            inflater = LayoutInflater.from(mContext);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.simple_spinner, parent, false);
        }
        StandardModel current = mList.get(position);
        HelviticaLight mStandarNAme = (HelviticaLight) convertView.findViewById(R.id.class_name_spinner);
        mStandarNAme.setText(current.getName());
        return convertView;
    }
}
