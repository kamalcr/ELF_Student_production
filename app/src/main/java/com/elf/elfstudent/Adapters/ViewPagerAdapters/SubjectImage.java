package com.elf.elfstudent.Adapters.ViewPagerAdapters;

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
    public static int PHYSICS = R.drawable.phy_small;
    public static int CHEMISTRY = R.drawable.chem_small;
    public static int BIOLOGY = R.drawable.bio_small;
    public static int COMPUTER = R.drawable.cs_small;

    public static String  SCIENCE_ID = "2";
    public static String  SOCIAL_ID = "3";
    public static String  MATHS_ID = "1";
    public static String  PHY_ID = "6";
    public static String MATHS_12 = "4";
    public static String  CHEM_ID = "5";
    public static String  BIO_ID = "8";
    public static String  COMP_ID = "7";

    public static int getSubjectImage( String subID) {


        Log.d("Image Id", "getSubjectImage: "+subID);
        switch (subID){


              //Tenth Science
            case  "3" :
                return SOCIAL;


            //Tenth Social
            case  "2" :

                return SCINEC_IMAGE;
            //tenth Maths
            case  "1" :
                return MATHS;

            //Twelth Maths
            case  "4" :
                return MATHS;

            //Twelth Physics
            case  "6" :
                return PHYSICS;
            //Twelth chemistry
            case  "5" :
                return CHEMISTRY;

            //Twelth Biology
            case  "8":
                return BIOLOGY;
            //twelth Computer
            case  "7" :
                return COMPUTER;


        }

        return 0;



    }

    public static String getSubjectName(String subjectId) {


        Log.d("Image Id", "getSubjectImage: "+subjectId);
        switch (subjectId){


            //Tenth Science
            case  "3" :
                return "Social";


            //Tenth Social
            case  "2" :

                return "Science";
            //tenth Maths
            case  "1" :
                return "Maths";

            //Twelth Maths
            case  "4" :
                return "Maths";

            //Twelth Physics
            case  "6" :
                return "Physics";
            //Twelth chemistry
            case  "5" :
                return "Chemistry";

            //Twelth Biology
            case  "8":
                return "Biology";
            //twelth Computer
            case  "7" :
                return "Computer Science";


        }
        return "null";

    }
}
