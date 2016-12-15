package com.elf.elfstudent.Utils;

import com.elf.elfstudent.R;

/**
 * Created by nandhu on 17/11/16.
 * IT retrieves the Big Image for All the Subjects
 */

public class SubjectBigImage {
//    // TODO: 17/11/16 add big subject images
    public static  int  SCINEC_IMAGE = R.drawable.science_big;
    public static int SOCIAL = R.drawable.social_big;
    public static int MATHS = R.drawable.maths_big;
    public static int PHYSICS = R.drawable.phy_big;
    public static int CHEMISTRY = R.drawable.chem_big;
    public static int BIOLOGY = R.drawable.bio_bigg;
    public static int COMPUTER = R.drawable.cs_big;

    // TODO: 8/11/16 add Subject Id
    public static String  SCIENCE_ID = "2";
    public static String  SOCIAL_ID = "3";
    public static String  MATHS_ID = "1";
    public static String  PHY_ID = "6";
    public static String MATHS_12 = "4";
    public static String  CHEM_ID = "5";
    public static String  BIO_ID = "8";
    public static String  COMP_ID = "7";

    public static int getBIgSubjectImage( String subID) {


        switch (subID){

            //Tenth Social
            case  "3" :
                return SOCIAL;


            //Tenth  Science
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
            default:
                return 0;

        }









    }
}


