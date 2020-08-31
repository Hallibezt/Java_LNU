package model;

import model.roles.Users;

public class Boat {
    private String type;
    private double length;
    private String registrationNumber;
    private Users owner;

    public Boat(String type, double length, String registrationNumber, Users owner){
        this.type=type;
        this.length = length;
        this.registrationNumber = registrationNumber;
        this.owner = owner;
    }

    public void changeRegNumber(String registrationNumber){
        this.registrationNumber = registrationNumber;
    }
    public void changeType(String type){
        this.type = type;
    }
    public void changeLength(double length){
        this.length = length;
    }
    public String getRegNumber(){
        return this.registrationNumber;
    }
    public String getType(){
        return this.registrationNumber;
    }
    public double getLength(){
        return this.length;
    }
    public Users getOwner(){return this.owner;}


}
