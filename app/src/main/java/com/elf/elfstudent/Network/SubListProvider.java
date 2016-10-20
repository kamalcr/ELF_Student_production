package com.elf.elfstudent.Network;

import com.android.volley.Response;
import com.elf.elfstudent.model.SubjectModelPrice;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by nandhu on 20/10/16.
 */

public class SubListProvider implements Response.Listener<JSONArray> {

    private SubListCallbacks mCallback = null;

    public SubListProvider(SubListCallbacks mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(JSONArray response) {

    }

    public interface SubListCallbacks {
        void SubList(List<SubjectModelPrice> mList);
    }
}
