package com.DronApp.Models;

import java.util.ArrayList;
import java.util.List;

public class Dron {

    private Coordinate coordinate;
    private List<String> movements;
    private String errorRoute;

    public String getErrorRoute() {
        return errorRoute;
    }

    public void setErrorRoute(String errorRoute) {
        this.errorRoute = errorRoute;
    }

    public Dron(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public List<String> getMovements() {
        if(movements == null){
            movements = new ArrayList<>();
        }
        return movements;
    }

    public void setMovements(List<String> movements) {
        this.movements = movements;
    }
}
