package com.elf.elfstudent.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.R;
import com.elf.elfstudent.model.StateModel;

import java.util.List;

/**
 * Created by nandhu on 17/10/16.
 * The Adapter which Provides View to State Spinner
 */
public class CustomSpinnerStateAdapter extends ArrayAdapter<StateModel> {
    //we Need Context to inflate the Layout
    private Context mContext;

    //The List of State Models
    private List<StateModel> mList;
    public CustomSpinnerStateAdapter(Context context, int resource, List<StateModel> data) {
        super(context, resource, data);
        this.mContext = context;
        this.mList = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {   // Ordinary view in Spinner, we use android.R.layout.simple_spinner_item
        return super.getView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {   // This view starts when we click the spinner.
        View row = convertView;
        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.custom_spinner, parent, false);
        }

        StateModel item = mList.get(position);

        if(item != null)
        {   // Parse the data from each object and set it.

            HelviticaLight boardname = (HelviticaLight) row.findViewById(R.id.spinner_name_text);


            if(boardname != null)
                boardname.setText(item.getStateName());

        }

        return row;
    }
}

