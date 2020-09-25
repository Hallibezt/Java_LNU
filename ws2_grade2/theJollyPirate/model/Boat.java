package model;

import controller.EnumValues;

import java.io.Serializable;

public  class Boat implements Serializable {
    private static final long serialVersionUID = 1989992313610046251L;
    private double length;
    private final String registrationNumber;
    private final User owner;
    private int location;
    private EnumValues.boatType type;
    private Price price = new Price();





    public Boat(EnumValues.boatType boatType, double length, String registrationNumber, User owner){
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

    public void setType(EnumValues.boatType type) {
        this.type = type;
    }
    public EnumValues.boatType getType() {
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

    public void getMoreUpdateInfo(Boat boat, Boat updatedBoat, double oldLength){
        this.price.setUpdatePrice(updatedBoat, boat.getType(), oldLength);
        this.location = boat.getLocation();

    }
}
