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
    private List<InstitutionModel> filteredInstituion, mListAll;

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
        HelviticaLight mInsName = (HelviticaLight)convertView.findViewById(R.id.ins_name);
        mInsName.setText(mList.get(position).getInsName());
        return convertView;
/*
        if (inflater == null){
            inflater = LayoutInflater.from(mContext);
        }
        if (convertView == null){

            //The Root View if Adapter
            convertView  = inflater.inflate(R.layout.institution_item_row,parent,false);
            //T
        }

        HelviticaLight mInsName = (HelviticaLight)convertView.findViewById(R.id.ins_name);
        mInsName.setText(mList.get(position).getInsName());
        HelviticaLight cityName = (HelviticaLight)convertView.findViewById(R.id.district_name);
        cityName.setText(mList.get(position).getCityName());

*/



    }
    /*

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        re
     /*   if (inflater == null){
            inflater = LayoutInflater.from(mContext);
        }
        if (convertView == null){

            //The Root View if Adapter
            convertView  = inflater.inflate(R.layout.institution_item_row_new,parent,false);

            //T
        }
        HelviticaLight mInsName = (HelviticaLight)convertView.findViewById(R.id.ins_name);
        mInsName.setText(mList.get(position).getInsName());
        /*

        HelviticaLight mInsName = (HelviticaLight)convertView.findViewById(R.id.ins_name);
        mInsName.setText(mList.get(position).getInsName());
        HelviticaLight cityName = (HelviticaLight)convertView.findViewById(R.id.district_name);
        cityName.setText(mList.get(position).getCityName());




        return convertView;
    }
    */



    public SchoolListAdapter(Context context, int resource, List<InstitutionModel> objects) {
        super(context, resource, objects);
        this.mContext  = context;
        this.mList = objects;
        mListAll = objects;
        filteredInstituion = new ArrayList<InstitutionModel>();
    }

    //The Class Responsible for Filtering Objects




    Filter institutionFilter = new Filter() {


        @Override
        public CharSequence convertResultToString(Object resultValue) {
            Log.d("ADAPTER", "convertResultToString: ");
            String insName = ((InstitutionModel)resultValue).getInsName();
            return insName;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            List<InstitutionModel> filteredList = (ArrayList<InstitutionModel>) results.values;

            if (results != null && results.count > 0) {
                clear();
                for (InstitutionModel instituion : filteredList) {
                    Log.d("ADAPTER", "Addingg Instituion to List: ");
                    add(instituion);
                }
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }



        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null) {
                Log.d("ADAPTER", "performFiltering: ");
                filteredInstituion.clear();
                for (InstitutionModel instittion : mListAll) {
                    if (instittion.getInsName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredInstituion.add(instittion);
                    }
                }
                filterResults.values = filteredInstituion;
                filterResults.count = filteredInstituion.size();
            }
            return filterResults;
        }
    };


}