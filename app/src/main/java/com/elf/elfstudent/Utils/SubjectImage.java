package com.elf.elfstudent.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.elf.elfstudent.R;

/**
 * Created by nandhu on 27/10/16.
 *The Image provider for Subject used in {@link com.elf.elfstudent.Adapters.SubjectHomeAdapter} and
 * {@link com.elf.elfstudent.Activities.SubjectViewActivity}
 */

public class SubjectImage {
    public static int SCINEC_IMAGE = R.drawable.sci_svg;
    public static int SOCIAL = R.drawable.soc_svg;
    public static int MATHS = R.drawable.maths;
    public static int PHYSICS = R.drawable.phy_svg;
    public static int CHEMISTRY = R.drawable.chem_svg;
    public static int BIOLOGY = R.drawable.bio_svg;
    public static int COMPUTER = R.drawable.cs_svg;
    public static String  SCIENCE_ID = "12";
    public static String  SOCIAL_ID = "13";
    public static String  MATHS_ID = "11";
    public static String  PHY_ID = "2";
    public static String MATHS_12 = "2";
    public static String  CHEM_ID = "3";
    public static String  BIO_ID = "";
    public static String  COMP_ID = "";

    public static int getSubjectImage( String subID) {


        switch (subID){


              //Tenth Science
            case  "12" :
                return SCINEC_IMAGE;


            //Tenth Social
            case  "13" :
                return SOCIAL;
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
            case  "4":
                return BIOLOGY;
            //twelth Computer
            case  "5" :
                return COMPUTER;


        }

        return 0;



    }
}
