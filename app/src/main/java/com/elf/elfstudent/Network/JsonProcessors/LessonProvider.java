package com.elf.elfstudent.Network.JsonProcessors;

import com.android.volley.Response;
import com.elf.elfstudent.model.Lesson;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by nandhu on 22/10/16.
 *
 * used in {@link com.elf.elfstudent.Activities.SubjectViewActivity}
 */
public class LessonProvider implements Response.Listener<JSONArray> {

    private List<Lesson> mlist = null;
    private SubjectLoaderCallback mCallback = null;

    public LessonProvider(SubjectLoaderCallback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(JSONArray response) {

    }

    public interface SubjectLoaderCallback{
        void setLessonList(List<Lesson> mLessons);
        void noLesson();
    }
}
