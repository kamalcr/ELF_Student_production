package com.elf.elfstudent.Network.JsonProcessors;

import com.android.volley.Response;

import org.json.JSONObject;

/**
 * Created by nandhu on 20/10/16.
 * used in {@link com.elf.elfstudent.Activities.ChangePasswordActivity}
 */
public class ChangePasswordHandler  implements Response.Listener<JSONObject>{
    private Callbacks mCallbacks = null;

    public ChangePasswordHandler(Callbacks mCallbacks) {
        this.mCallbacks = mCallbacks;
    }

    @Override
    public void onResponse(JSONObject response) {



        //
        //mCallbacks.passWordChanged();
    }

    public interface Callbacks{
        void passWordChanged(String newPassword);
        void passwordNotChanged();
    }
}
