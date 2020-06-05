package com.DronApp.Utilis;

public class Utils {

    private Utils(){}

    public static boolean isEmpty(String string){
        return string.isEmpty() || string==null? Boolean.TRUE : Boolean.FALSE;
    }
}
