package com.elf.elfstudent.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.elf.elfstudent.R;

/**
 * Created by nandhu on 27/10/16.
 *
 */

public class SubjectImage {
    public static int SCINEC_IMAGE = R.drawable.ic_science_300_80;
    public static int SOCIAL = R.drawable.ic_social_300_80;
    public static int MATHS = R.drawable.ic_maths_300_80;
    public static int PHYSICS = R.drawable.ic_science_300_80;
    public static int CHEMISTRY = R.drawable.ic_science_300_80;
    public static int BIOLOGY = R.drawable.ic_science_300_80;
    public static int COMPUTER = R.drawable.ic_science_300_80;
    public static String  SCIENCE_ID = "12";
    public static String  SOCIAL_ID = "13";
    public static String  MATHS_ID = "11";
    public static String  PHY_ID = "2";
    public static String MATHS_12 = "2";
    public static String  CHEM_ID = "3";
    public static String  BIO_ID = "";
    public static String  COMP_ID = "";

    public static Drawable getSubjectImage(Context mContext, String subID) {


        switch (subID){


              //Tenth Science
            case  "12" :
                return ContextCompat.getDrawable(mContext,SCINEC_IMAGE);


            //Tenth Social
            case  "13" :
                return ContextCompat.getDrawable(mContext,SOCIAL);

            //tenth Maths
            case  "11" :
                return ContextCompat.getDrawable(mContext,MATHS);

            //Twelth Maths
            case  "1" :
                return ContextCompat.getDrawable(mContext,MATHS);

            //Twelth Physics
            case  "2" :
                return ContextCompat.getDrawable(mContext,PHYSICS);

            //Twelth chemistry
            case  "3" :
                return ContextCompat.getDrawable(mContext,CHEMISTRY);

            //Twelth Biology
            case  "4" :
                return ContextCompat.getDrawable(mContext,BIOLOGY);
            //twelth Computer
            case  "5" :
                return ContextCompat.getDrawable(mContext,COMPUTER);


        }

        return null;



    }
}
