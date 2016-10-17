package com.elf.elfstudent.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.R;
import com.elf.elfstudent.model.BoardModel;

import java.util.List;

/**
 * Created by nandhu on 17/10/16.
 * The Spinner Which Assign Board Values
 * The Reason This has Separate ADapter is that Each Board has Corresponding Id with it
 * eg CBSE - 0 ,Samacheer - 1 .. and we use ID in  Registration Webservice
 */
public class CustomSpinnerBoardAdapter extends ArrayAdapter<BoardModel> {
    private Context context;
    List<BoardModel> data = null;

    public CustomSpinnerBoardAdapter(Context context, int resource, List<BoardModel> data)
    {
        super(context, resource, data);
        this.context = context;
        this.data = data;
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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.custom_spinner, parent, false);
        }

        BoardModel item = data.get(position);

        if(item != null)
        {   // Parse the data from each object and set it.

            HelviticaLight boardname = (HelviticaLight) row.findViewById(R.id.spinner_name_text);


            if(boardname != null)
                boardname.setText(item.getName());

        }

        return row;
    }
}
