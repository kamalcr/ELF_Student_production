package com.elf.elfstudent.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.elf.elfstudent.R;

/**
 * Created by Kri on 16-12-2016.
 */

public class Exitview extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);




    }

    @Override
    public void onBackPressed() {

       SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor= pref.edit();

        editor.remove("EMAIL");// will delete key email
        editor.remove("mPassword"); // will delete key name
        editor.remove("mPhoneBox"); //will delete password
        editor.remove("mNameBox");// will delete phone number

        editor.commit();

        editor.clear();
        editor.commit();

        final AlertDialog.Builder builder = new AlertDialog.Builder(Exitview.this);
        builder.setMessage("Are you sure u want to exit ?");
        builder.setCancelable(true);


        builder.setNegativeButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

                Log.d("cancel", "onClick: ");
            }
        });
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }



}
