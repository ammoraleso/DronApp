package com.DronApp.Controllers;

import com.DronApp.Constants.Constants;
import com.DronApp.Exception.FileException;
import com.DronApp.Models.Coordinate;
import com.DronApp.Models.Dron;
import com.DronApp.Utilis.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileController {

    private Dron dron;
    private static List<String> filesDrones;
    private static FileController fileController;

    /**
     * Singleon Instance
     */
    public static FileController getFileControllerInstace() {
        if(fileController == null){
            return fileController = new FileController();
        }
        return fileController;
    }

    public LinkedHashMap<String,Dron> obtainRoutes(int dronLimit) throws FileException {
        listFileForFolder();
        LinkedHashMap<String,Dron> listOfDrons = new LinkedHashMap<>();
        for(String fileDron : filesDrones){
            String error = null;
            dron = new Dron(new Coordinate(0, 0, Constants.NORTH));
            try {
                String path = Constants.FOLDER_PATH_IN.concat("/").concat(fileDron);
                File fileIn = readFile(path,dronLimit);
                FileReader fileReader = new FileReader(fileIn);

                BufferedReader buffer = new BufferedReader(fileReader);
                String line;
                for (int i = 0;(line = buffer.readLine()) != null; i++) {
                    if(line.equals("")){
                        continue;
                    }
                    if (i >= dronLimit) {
                        error = "The file have exced the limit of dron";
                        break;
                    }
                    if (!validateStructure(line)) {
                        error = "Structure of routes is invalid";
                        break;
                    }
                    if(!DronController.validateCoverage(line,dron)){
                        error = "Route is out of range";
                        continue;
                    }
                }
            } catch (Exception e) {
                throw new FileException("FileController - ObtainRoutes, Error: " + e, e);
            }
            if(error!=null){
                dron.setErrorRoute(error);
            }else{
                if(dron.getMovements()==null || dron.getMovements().isEmpty()){
                    dron.setErrorRoute("File " + fileDron + " empty for dron ");
                }
            }
            listOfDrons.put(fileDron,dron);
        }
        return listOfDrons;
    }

    private static File readFile(String path,int dronLimit) throws FileException{
        if ( Utils.isEmpty(path) || dronLimit <= 0) {
            throw new FileException("Path or capacity of drone is undefine");
        }
        File fileIn = new File(path);
        if (!fileIn.exists()) {
            throw new FileException("No file found in specific path : " + path);
        }
        return fileIn;
    }

    public static void writeFile(LinkedHashMap<String,Dron> listOfDrones){
        FileWriter outputFile = null;
        for (Map.Entry<String, Dron> entry : listOfDrones.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            PrintWriter pw;
            try
            {
                outputFile = new FileWriter(Constants.FOLDER_PATH_OUT.concat("/").concat(key.replace("in","out")));
                pw = new PrintWriter(outputFile);

                if(((Dron) value).getErrorRoute()==null){
                    for(String mov : ((Dron) value).getMovements()){
                        pw.println(mov);
                    }
                }else{
                    pw.println(((Dron) value).getErrorRoute());
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != outputFile){
                        outputFile.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private static boolean validateStructure(String line) {

        Pattern pattern = Pattern.compile(Constants.REGEX_STRUCTURE_LINE);
        Matcher matcher = pattern.matcher(line);
        if(!matcher.find()) {
            return  Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private static void listFileForFolder() {
        filesDrones = new ArrayList();
        final File file = new File(Constants.FOLDER_PATH_IN);
        for (final File fileIn : file.listFiles()) {
            if (fileIn.isDirectory()) {
                listFileForFolder();
            } else {
                filesDrones.add(fileIn.getName());
                //System.out.println(fileIn.getName());
            }
        }
    }

}
