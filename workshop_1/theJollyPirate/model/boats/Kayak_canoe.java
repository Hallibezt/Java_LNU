package model.boats;

import model.roles.Users;

public class Kayak_canoe extends Boat {
    private String registrationNumber;
    private double length;
    private Users owner;

    public Kayak_canoe( double length, String registrationNumber, Users owner){
        this.length = length;
        this.owner = owner;
        this.registrationNumber=registrationNumber;
    }

    @Override
    public void changeRegNumber(String registrationNumber) {

    }



    @Override
    public void changeLength(double length) {

    }

    @Override
    public String getRegNumber() {
        return null;
    }

    @Override
    public String getType() {
        return "Kayak_canoe";
    }

    @Override
    public double getLength() {
        return 0;
    }

    @Override
    public Users getOwner() {
        return null;
    }
}
