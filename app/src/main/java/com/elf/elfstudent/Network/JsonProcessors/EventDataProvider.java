package com.elf.elfstudent.Network.JsonProcessors;

import com.android.volley.Response;
import com.elf.elfstudent.model.StudentEvent;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by nandhu on 11/11/16.
 */
public class EventDataProvider  implements Response.Listener<JSONArray>{

    private EventDataCallbacls mCallback = null;

    public EventDataProvider(EventDataCallbacls mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(JSONArray response) {

    }

    public interface EventDataCallbacls{
        void EventsReceived(List<StudentEvent> mList);
        void NoEvents();
    }
}
