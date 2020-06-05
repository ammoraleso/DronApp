package com.DronApp.Interfaces;

import com.DronApp.Constants.Constants;
import com.DronApp.Exception.FileException;
import com.DronApp.Models.Dron;
import com.DronApp.Utilis.Utils;

import java.io.File;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface FileInterface {

    void writeFile(Map<String,Dron> listOfDrones);
    static File readFile(String path, int dronLimit) throws FileException{
        if ( Utils.isEmpty(path) || dronLimit <= 0) {
            throw new FileException("Path or capacity of drone is undefine");
        }
        File fileIn = new File(path);
        if (!fileIn.exists()) {
            throw new FileException("No file found in specific path : " + path);
        }
        return fileIn;
    }

    static boolean validateStructure(String line) {
        Pattern pattern = Pattern.compile(Constants.REGEX_STRUCTURE_LINE);
        Matcher matcher = pattern.matcher(line);
        if(!matcher.find()) {
            return  Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
