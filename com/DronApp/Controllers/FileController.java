package com.DronApp.Controllers;

import com.DronApp.Constants.Constants;
import com.DronApp.Exception.FileException;
import com.DronApp.Interfaces.FileInterface;
import com.DronApp.Models.Coordinate;
import com.DronApp.Models.Dron;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FileController implements FileInterface {

    private static List<String> filesDrones;
    private static FileController fileController;

    public static List<String> getFilesDrones() {
        return filesDrones;
    }

    /**
     * Singleon Instance
     */
    public static FileController getFileControllerInstace() {
        if(fileController == null){
            fileController = new FileController();
            return fileController;
        }
        return fileController;
    }

    @Override
    public void writeFile(Map<String,Dron> listOfDrones) throws IOException {
        FileWriter outputFile = null;
        for (Map.Entry<String, Dron> entry : listOfDrones.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            PrintWriter pw = null;
            outputFile = new FileWriter(Constants.FOLDER_PATH_OUT.concat("/").concat(key.replace("in","out")));
            pw = new PrintWriter(outputFile);
            try{
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
                    if(pw != null){
                        pw.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public Map<String,Dron> obtainRoutes(int dronLimit) throws FileException {
        listFileForFolder();
        LinkedHashMap<String,Dron> listOfDrons = new LinkedHashMap<>();
        for(String fileDron : filesDrones){
            String error = null;
            Dron dron = new Dron(new Coordinate(0, 0, Constants.NORTH));
            String path = Constants.FOLDER_PATH_IN.concat("/").concat(fileDron);

            File fileIn = FileInterface.readFile(path,dronLimit);
            try (FileReader fileReader = new FileReader(fileIn); BufferedReader buffer = new BufferedReader(fileReader)){
                String line;
                for (int i = 0;(line = buffer.readLine()) != null; i++) {
                    if(!line.equals("")){
                        if (i >= dronLimit) {
                            error = "The file have exced the limit of dron";
                        } else if (!FileInterface.validateStructure(line)) {
                            error = "Structure of routes is invalid";
                        } else if(!DronController.validateCoverage(line,dron)){
                            error = "Route is out of range";
                        }
                    }
                }
            } catch (Exception e) {
                throw new FileException("FileController - ObtainRoutes, Error: " + e, e);
            }
            if(error!=null){
                dron.setErrorRoute(error);
            }else if(dron.getMovements()==null || dron.getMovements().isEmpty()){
                dron.setErrorRoute("File " + fileDron + " empty for dron ");
            }
            listOfDrons.put(fileDron,dron);
        }
        return listOfDrons;
    }

    public static void listFileForFolder() {
        filesDrones = new ArrayList<>();
        final File file = new File(Constants.FOLDER_PATH_IN);
        for (final File fileIn : file.listFiles()) {
            if (fileIn.isDirectory()) {
                listFileForFolder();
            } else {
                filesDrones.add(fileIn.getName());
            }
        }
    }
}
