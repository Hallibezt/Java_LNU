package model;

import java.io.Serializable;

public  class Boat implements Serializable {
    private double length;
    private  String registrationNumber;
    private  User owner;
    private int location;
     String type;
    private Price price;





    public Boat(String boatType, double length, String registrationNumber, User owner){
        this.length = length;
        this.registrationNumber = registrationNumber;
        this.owner = owner;
        this.type = boatType;
    }

    public void changeLength(double length) {
        this.length = length;
    }

    public void addLocation(int location) {
        this.location = location;
    }

    public int getLocation() {
        return location;
    }

    public String getRegNumber() {
        return this.registrationNumber;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return this.type;
    }

    public double getLength() {
        return this.length;
    }

    public User getOwner() {
        return this.owner;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Price getPrice(){
        return this.price;
    }
}
