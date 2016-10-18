package com.elf.elfstudent.Network;

import android.util.Log;

import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

/**
 * Created by nandhu on 18/10/16.
 */

public class ErrorHandler implements Response.ErrorListener {


    private static final String TAG = "Volley Error";

    @Override
    public void onErrorResponse(VolleyError error) {

        if (error instanceof NetworkError){

            Log.d(TAG, "onErrorResponse: Network Error");

        }else if (error instanceof TimeoutError){
            Log.d(TAG, "onErrorResponse: TimeOut Error");
        }
        else if (error instanceof ServerError){
            Log.d(TAG, "onErrorResponse: server Error");
        }

    }
}
