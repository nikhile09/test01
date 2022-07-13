package org.example;

import org.utils.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class Fleet {

    private List<Vehicle> vehicles;

    public Fleet() {
        vehicles = new ArrayList<>();
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public void addVehicles(Vehicle vehicle){
        vehicles.add(vehicle);
    }
}
