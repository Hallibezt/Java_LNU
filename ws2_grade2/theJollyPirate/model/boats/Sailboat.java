package model.boats;

import model.roles.User;

public class Sailboat extends Boat {
    private double length;
    private final String registrationNumber;
    private final User owner;
    private int location;



    public Sailboat(double length, String registrationNumber, User owner){
        this.length = length;
        this.registrationNumber = registrationNumber;
        this.owner = owner;
    }



    @Override
    public void changeLength(double length) {
            this.length = length;
    }


    @Override
    public void addLocation(int location) {
        this.location = location;
    }

    @Override
    public int getLocation() {
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
    public User getOwner() {
        return this.owner;
    }
}
