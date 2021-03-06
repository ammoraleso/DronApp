package com.DronApp.Controllers;

import com.DronApp.Constants.Constants;
import com.DronApp.Exception.FileException;
import com.DronApp.Models.Dron;


import java.util.LinkedHashMap;

public class DronController {

    private DronController() {
        //not called
    }

    private static FileController fileController = FileController.getFileControllerInstace();

    public static String beginOrders() {
        try {

            LinkedHashMap<String,Dron> listOfDrons =
                    (LinkedHashMap<String, Dron>) fileController.obtainRoutes(Constants.DRONNUMBER);

            fileController.writeFile(listOfDrons);

        } catch (FileException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "DronController error , " + e.getMessage();
        }
        return "The order was genedated succesfully";
    }

    public static boolean validateCoverage(String line,Dron dron) {

        char[] movs = line.toCharArray();

        for(char mov : movs){

            switch (dron.getCoordinate().getDirection()) {
                case Constants.NORTH:
                    if (mov == Constants.MOV_A) {
                        dron.getCoordinate().setY(dron.getCoordinate().getY() + 1);
                    } else if (mov == Constants.MOV_I) {
                        dron.getCoordinate().setDirection(Constants.WEAST);
                    } else {
                        dron.getCoordinate().setDirection(Constants.EAST);
                    }
                    break;
                case Constants.SOUTH:
                    if (mov == Constants.MOV_A) {
                        dron.getCoordinate().setY(dron.getCoordinate().getY() - 1);
                    } else if (mov == Constants.MOV_I) {
                        dron.getCoordinate().setDirection(Constants.EAST);
                    } else {
                        dron.getCoordinate().setDirection(Constants.WEAST);
                    }
                    break;
                case Constants.EAST:
                    if (mov == Constants.MOV_A) {
                        dron.getCoordinate().setX(dron.getCoordinate().getX() + 1);
                    } else if (mov == Constants.MOV_I) {
                        dron.getCoordinate().setDirection(Constants.NORTH);
                    } else {
                        dron.getCoordinate().setDirection(Constants.SOUTH);
                    }
                    break;
                case Constants.WEAST:
                    if (mov == Constants.MOV_A) {
                        dron.getCoordinate().setX(dron.getCoordinate().getX() -1);
                    } else if (mov == Constants.MOV_I) {
                        dron.getCoordinate().setDirection(Constants.SOUTH);
                    } else {
                        dron.getCoordinate().setDirection(Constants.NORTH);
                    }
                    break;
                default:
                    break;
            }

        }

        StringBuilder movementSb = new StringBuilder("(").append(dron.getCoordinate().getX())
                .append(",")
                .append(dron.getCoordinate().getY())
                .append(") direccion ")
                .append(dron.getCoordinate().getDirection());

        dron.getMovements().add(movementSb.toString());
        return  Math.abs(dron.getCoordinate().getX()) > 10 || Math.abs(dron.getCoordinate().getY()) > 10 ? Boolean.FALSE : Boolean.TRUE;
    }
}
