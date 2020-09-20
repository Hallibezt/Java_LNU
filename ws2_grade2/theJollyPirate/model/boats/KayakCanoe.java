package model.boats;

import model.roles.User;

public class KayakCanoe extends Boat {
    private final String registrationNumber;
    private double length;
    private final User owner;
    private int location;


    public KayakCanoe(double length, String registrationNumber, User owner){
        this.length = length;
        this.owner = owner;
        this.registrationNumber=registrationNumber;
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
        return "Kayak_canoe";
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
