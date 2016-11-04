package com.elf.elfstudent.Network;

import android.util.Log;

import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

/**
 * Created by nandhu on 18/10/16.
 */

public class ErrorHandler implements Response.ErrorListener {



    ErrorHandlerCallbacks mCallback;

    public ErrorHandler(ErrorHandlerCallbacks mCallback) {
        this.mCallback = mCallback;
    }

    private static final String TAG = "Volley Error";

    @Override
    public void onErrorResponse(VolleyError error) {

        Log.d(TAG, "onErrorResponse: "+error.getLocalizedMessage());


        if (error instanceof NetworkError){

           mCallback.NetworkError();

        }else if (error instanceof TimeoutError){
          mCallback.TimeoutError();
        }
        else if (error instanceof ServerError){
           mCallback.ServerError();
        }
        else{
            Log.d(TAG, "onErrorResponse: "+error.getMessage());
        }
    }

    public interface ErrorHandlerCallbacks{
        void TimeoutError();
        void NetworkError();
        void ServerError();
    }
}
