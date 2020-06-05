package com.DronApp.Utilis;

public class Utils {

    private Utils(){}

    public static boolean isEmpty(String string){
        if(string==null){
            return Boolean.TRUE;
        }
        if(string.isEmpty()){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
