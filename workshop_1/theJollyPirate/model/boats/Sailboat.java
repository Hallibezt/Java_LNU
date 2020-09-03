package model.boats;

import model.Berths;
import model.roles.Users;

public class Sailboat extends Boat {
    private double length;
    private String registrationNumber;
    private Users owner;
    private int location;

    public Sailboat(double length, String registrationNumber, Users owner){
        this.length = length;
        this.registrationNumber = registrationNumber;
        this.owner = owner;
    }


    @Override
    public void changeRegNumber(String registrationNumber) {
            this.registrationNumber = registrationNumber;
    }


    @Override
    public void changeLength(double length) {
            this.length = length;
    }

    @Override
    public void changeOwner(Users user) {
            this.owner = user;
    }

    @Override
    public void addLocation(int location) {
        this.location = location;
    }

    @Override
    public int getLoacation() {
        return location;
    }

    @Override
    public String getRegNumber() {
        return this.registrationNumber;
    }

    @Override
    public String getType() {
        return "Sailboat";
    }

    @Override
    public double getLength() {
        return this.length;
    }

    @Override
    public Users getOwner() {
        return this.owner;
    }
}
