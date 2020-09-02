package model.boats;

import model.roles.Users;

public class Motorsailer extends Boat {

    private double length;
    private String registrationNumber;
    private Users owner;

    public Motorsailer( double length, String registrationNumber, Users owner){
        ;
        this.length = length;
        this.registrationNumber = registrationNumber;
        this.owner = owner;
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
        return "Motorsailor";
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
