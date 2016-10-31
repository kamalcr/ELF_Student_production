package com.elf.elfstudent.DataStorage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by nandhu on 17/10/16.
 *
 */

public class DataStore {
    private static final String SHARED_PREFS_FILE  = "ELFSTUDENT";
    private static final String USER_NAME_TAG = "LOGIN_USER_NAME";
    private static final String PHONE_NUMBER_TAG  = "PHONE_NUMBER";
    private static final String EMAIL_ID_TAG = "EMAIL_ID";
    private static final String STATE_ID = "STATE_ID";
    private static final String STUDENT_ID = "STUDENT_ID";
    private static final String STATE_NAME = "STATE_NAME";
    private static final String INSTITUTION_ID = "INSTITUTION";
    private static final String INSTITUION_NAME  = "INSTITUTION_NAME";
    private static final String BOARD_ID = "BOARD_ID";
    private static final String BOARD_NAME  = "BOARD_NAME";

    private static final String IS_FIRST_TIME = "IS_FIRST_TIME";
    private static final String PASSWORD = "PASSWORD";
    private static final String STUDENT_STANDARD = "STANDARD";

    private static final String COMPUTER = "COMPUTER";
    private static final String BIOLOGY = "BIOLOGY";
    private static final String SUBJECT_SELECTED = "SUBJECT_SELECTED";


    private static DataStore mStore = null;

    public static synchronized DataStore getStorageInstance(Context context) {
        if (mStore == null) {
            mStore = new DataStore(context);
        }
        return mStore;
    }


    private SharedPreferences.Editor editor = null;
    private SharedPreferences mSharedPrefrences = null;

    private Context mContext = null;
    public DataStore(Context mContext) {
        this.mContext = mContext;
        mSharedPrefrences = (SharedPreferences) this.mContext.getSharedPreferences(SHARED_PREFS_FILE,Context.MODE_PRIVATE);
        if (mSharedPrefrences != null){
            editor = mSharedPrefrences.edit();
        }
    }


    //Board & Board ID
    public void setBoardId(String boardId) {
        editor.putString(BOARD_ID,boardId);
        editor.commit();

    }

    public void setBoardName(String boardName) {
        editor.putString(BOARD_NAME, boardName);
        editor.commit();
    }

    public void setEmailId(String emailIdTag) {
        editor.putString(EMAIL_ID_TAG, emailIdTag);
        editor.commit();
    }

    public void setInstituionName(String instituionName) {
        editor.putString(INSTITUION_NAME, instituionName);
        editor.commit();
    }

    public void setInstitutionId(String institutionId) {
        editor.putString(INSTITUTION_ID, institutionId);
        editor.commit();
    }

    public void setStateId(String stateId) {
        editor.putString(STATE_ID, stateId);
        editor.commit();
    }

    public void setStateName(String stateName) {
        editor.putString(STATE_NAME,stateName);
        editor.commit();
    }

    public  void setUserName(String userNameTag) {
        editor.putString(USER_NAME_TAG, userNameTag);
        editor.commit();
    }

    public  void setPhoneNumberTag(String phoneNumberTag) {
        editor.putString(PHONE_NUMBER_TAG, phoneNumberTag);
        editor.commit();
    }

    public void setStudentId(String studentId) {
        editor.putString(STUDENT_ID, studentId);
        editor.commit();

    }

    public  String getStateId() {
        return mSharedPrefrences.getString(STATE_ID,null);
    }

    public  String getStudentId() {
        return mSharedPrefrences.getString(STUDENT_ID,null);
    }

    public  String getStateName() {
        return mSharedPrefrences.getString(STATE_NAME,null);
    }

    public  String getUserName() {
        return mSharedPrefrences.getString(USER_NAME_TAG,null);
    }

    public  String getPhoneNumber() {
        return mSharedPrefrences.getString(PHONE_NUMBER_TAG,null);
    }

    public  String getBoardId() {
        return mSharedPrefrences.getString(BOARD_ID,null);
    }

    public  String getBoardName() {
        return mSharedPrefrences.getString(BOARD_NAME,null);
    }

    public  String getEmailId() {
        return mSharedPrefrences.getString(EMAIL_ID_TAG,null);
    }

    public  String getInstituionName() {
        return mSharedPrefrences.getString(INSTITUION_NAME,null);
    }
    public  String getInstituionId() {
        return mSharedPrefrences.getString(INSTITUTION_ID,null);


    }


    public  void setIsFirstTime(boolean value) {
        editor.putBoolean(IS_FIRST_TIME,value);
        editor.commit();
    }

    public  boolean isFirstTime(){
        return mSharedPrefrences.getBoolean(IS_FIRST_TIME,true);
    }

    public String getPassWord() {
        return mSharedPrefrences.getString(PASSWORD,null);
    }

    public String getStandard() {
        return mSharedPrefrences.getString(STUDENT_STANDARD,null);
    }

    public  void setStudentStandard(String studentStandard) {

        Log.d("PREFS", "setStudentStandard:  "+studentStandard);
         editor.putString(STUDENT_STANDARD,studentStandard);
        editor.apply();
    }

    public void setPassword(String password) {
        editor.putString(PASSWORD,password);
        editor.apply();
    }

    public void setStudentPrefrerredSubject(int i){
        //Zero for Computer
        if (i == 0){
            Log.d("PREFS", "Saving group computer:");
            editor.putString(SUBJECT_SELECTED,COMPUTER);
            editor.apply();
        }

        //one for bio
        else{
            Log.d("PREFS", "Saving group Bio:");
            editor.putString(SUBJECT_SELECTED,BIOLOGY);
            editor.apply();
        }
    }

    public String getStudentGroup(){
        return mSharedPrefrences.getString(SUBJECT_SELECTED,null);
    }
}


