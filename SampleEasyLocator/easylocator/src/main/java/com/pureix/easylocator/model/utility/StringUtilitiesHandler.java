package com.pureix.easylocator.model.utility;

/**
 * Created by M.Hayle on 6/22/2016.
 */
public class StringUtilitiesHandler
{
    //===================================================================================================================
    public static boolean StringEqual(String str1, String Str2)
    {
        //if(str1.compareTo(Str2) == 0)
        if(str1.equalsIgnoreCase(Str2))
        {
            return true;
        }else
        {
            return false;
        }
    }
    //===================================================================================================================
}
