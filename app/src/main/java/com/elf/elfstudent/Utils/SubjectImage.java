package com.elf.elfstudent.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.elf.elfstudent.R;

/**
 * Created by nandhu on 27/10/16.
 *The Image provider for Subject used in {@link com.elf.elfstudent.Adapters.SubjectHomeAdapter} and
 * {@link com.elf.elfstudent.Activities.SubjectViewActivity}
 */

public class SubjectImage {
    public static  int  SCINEC_IMAGE = R.drawable.science_small;
    public static int SOCIAL = R.drawable.social_small;
    public static int MATHS = R.drawable.maths_small;
    public static int PHYSICS = R.drawable.science_small;
    public static int CHEMISTRY = R.drawable.chem_small;
    public static int BIOLOGY = R.drawable.science_small;
    public static int COMPUTER = R.drawable.cs_small;

    // TODO: 8/11/16 add Subject Id
    public static String  SCIENCE_ID = "12";
    public static String  SOCIAL_ID = "13";
    public static String  MATHS_ID = "11";
    public static String  PHY_ID = "2";
    public static String MATHS_12 = "1";
    public static String  CHEM_ID = "3";
    public static String  BIO_ID = "15";
    public static String  COMP_ID = "14";

    public static int getSubjectImage( String subID) {


        Log.d("Image Id", "getSubjectImage: "+subID);
        switch (subID){


              //Tenth Science
            case  "13" :
                return SOCIAL;


            //Tenth Social
            case  "12" :

                return SCINEC_IMAGE;
            //tenth Maths
            case  "11" :
                return MATHS;

            //Twelth Maths
            case  "1" :
                return MATHS;

            //Twelth Physics
            case  "2" :
                return PHYSICS;
            //Twelth chemistry
            case  "3" :
                return CHEMISTRY;

            //Twelth Biology
            case  "15":
                return BIOLOGY;
            //twelth Computer
            case  "14" :
                return COMPUTER;


        }

        return 0;



    }
}
