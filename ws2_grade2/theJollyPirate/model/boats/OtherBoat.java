package model.boats;

import model.Berths;
import model.roles.Users;

public class OtherBoat extends Boat{
    private double length;
    private String registrationNumber;
    private Users owner;
    private int location;
    private String color;


    public OtherBoat( double length, String registrationNumber, Users owner){
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
    public int getLoacation() {
        return location;
    }

    @Override
    public String getRegNumber() {
        return this.registrationNumber;
    }

    @Override
    public String getType() {
        return "Other";
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
