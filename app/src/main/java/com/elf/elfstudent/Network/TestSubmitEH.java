package com.elf.elfstudent.Network;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by nandhu on 13/11/16.
 *
 */
public class TestSubmitEH  implements Response.ErrorListener{

    private ErrorCallbacks mErrorCallbacks  = null;

    public TestSubmitEH(ErrorCallbacks mErrorCallbacks) {
        this.mErrorCallbacks = mErrorCallbacks;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        mErrorCallbacks.ErrorOccurred();


    }

   public interface ErrorCallbacks{
        void ErrorOccurred();
    }
}
