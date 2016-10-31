package com.elf.elfstudent;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.elf.elfstudent.Activities.EmailActivityPage;
import com.elf.elfstudent.Activities.HomeActivity;
import com.elf.elfstudent.Activities.RegisterActivity;
import com.elf.elfstudent.DataStorage.DataStore;


/*This First Activty that gets launcehd ,
*
* Redirets to Register Activity if first time else normal Activty*/

public class SplashActivity extends AppCompatActivity {


    private static final String TAG = "ELF";
    private static final long SPLASH_TIME_OUT = 2000;
    private DataStore dataStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        dataStore = DataStore.getStorageInstance(getApplicationContext());

        //Check whether user already logged in or not
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                if (isFirstTime()){
                    Log.d(TAG, "onCreate: going to Registration");
                    Intent i = new Intent(getApplicationContext(),EmailActivityPage.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
                else{
                    Log.d(TAG, "goint to Home Activity");
                    Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                }
                finish();
            }
        }, SPLASH_TIME_OUT);




    }

    private boolean isFirstTime() {

        Log.d(TAG, "isFirstTime: "+dataStore.isFirstTime());
        return dataStore.isFirstTime();
    }
}
