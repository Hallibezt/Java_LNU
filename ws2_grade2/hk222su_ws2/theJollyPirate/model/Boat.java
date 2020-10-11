package model;
import model.enums.BoatType;

import java.io.Serializable;

public  class Boat implements Serializable {
    private static final long serialVersionUID = 1989992313610046251L; //For the serializer, if it is not set then i can happen that ID do not match
    private double length;
    private final String registrationNumber;
    private int location;
    private BoatType type;
    private Price price = new Price();





    public Boat(BoatType boatType, double length, String registrationNumber){
        this.length = length;
        this.registrationNumber = registrationNumber;
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

    public void setPrice(Price price) {
        this.price = price;
    }

    public Price getPriceObject(){
        return this.price;
    }

    public void getMoreUpdateInfo(Boat boat, Boat updatedBoat, double oldLength){
        this.price.setUpdatePrice(updatedBoat, boat.getType(), oldLength);
        this.location = boat.getLocation();
    }
}
