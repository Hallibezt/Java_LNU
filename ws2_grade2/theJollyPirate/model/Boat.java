package model;
import model.enums.BoatType;

import java.io.Serializable;

public  class Boat implements Serializable {
    private static final long serialVersionUID = 1989992313610046251L;
    private double length;
    private final String registrationNumber;
    private final Member owner;
    private int location;
    private BoatType type;
    private Price price = new Price();





    public Boat(BoatType boatType, double length, String registrationNumber, Member owner){
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

    public void setType(BoatType type) {
        this.type = type;
    }
    public BoatType getType() {
        return this.type;
    }

    public double getLength() {
        return this.length;
    }

    public Member getOwner() {
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
