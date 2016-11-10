package com.elf.elfstudent.Network.JsonProcessors;

import com.android.volley.Response;
import com.elf.elfstudent.model.StudentRequestModel;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by nandhu on 10/11/16.
 *
 */
public class RequestdataProvider  implements Response.Listener<JSONArray>{



    private List<StudentRequestModel> mList = null;
    private RequestCallbacks mCallback = null;

    public RequestdataProvider(RequestCallbacks mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(JSONArray response) {

    }


    public interface RequestCallbacks{
        void setNotification(List<StudentRequestModel> mList);
        void noRequest();
    }
}
