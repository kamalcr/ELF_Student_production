package com.elf.elfstudent.Utils;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.elf.elfstudent.Adapters.AllTestAdapter;
import com.elf.elfstudent.R;
import com.elf.elfstudent.model.AllTestModels;
import com.google.firebase.crash.FirebaseCrash;

import org.w3c.dom.Text;

import java.security.PrivateKey;

/**
 * Created by nandhu on 2/12/16.
 *
 */

public class TestChildHolder extends ChildViewHolder {

    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */


        //The Textview in this holder
   public TextView mText;
    public Button writeText;
    public RelativeLayout mLayout ;
     public TestChildHolder(View itemView) {
        super(itemView);
         mLayout = (RelativeLayout) itemView.findViewById(R.id.test_child_layout);
         mText = (TextView) itemView.findViewById(R.id.test_desc);
         writeText = (Button) itemView.findViewById(R.id.write_test_button);
    }

    public  void bind(final AllTestModels testList, final AllTestAdapter.onTestClicked mCallback) {
        mText.setText(testList.getmTestDetail());

        writeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCallback != null){
                    try {
                        mCallback.showTestWriteaPageFor(testList.getmTestId(),testList.getmSubjectId());

                    }
                    catch (Exception ew){
                        FirebaseCrash.log("Exception in calling Inteface ");
                    }
                }
            }
        });

    }
}
