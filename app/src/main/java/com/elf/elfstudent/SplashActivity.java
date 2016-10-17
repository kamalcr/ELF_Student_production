package com.elf.elfstudent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.elf.elfstudent.Activities.HomeActivity;
import com.elf.elfstudent.Activities.RegisterActivity;
import com.elf.elfstudent.DataStorage.DataStore;


/*This First Activty that gets launcehd ,
*
* Redirets to Register Activity if first time else normal Activty*/

public class SplashActivity extends AppCompatActivity {


    private DataStore dataStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        dataStore = DataStore.getStorageInstance(getApplicationContext());

        //Check whether user already logged in or not

         if (isFirstTime()){
             Intent i = new Intent(this,RegisterActivity.class);
             i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
             i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             startActivity(i);
         }
        else{
             Intent i = new Intent(this,HomeActivity.class);
             i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
             i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             startActivity(i);

         }


    }

    private boolean isFirstTime() {
        return dataStore.isFirstTime();
    }
}
