package model.boats;

import model.roles.User;

import java.io.Serializable;

public abstract class Boat implements Serializable {


    public abstract void changeLength(double length);
    public abstract void addLocation(int location);
    public abstract int getLocation();
    public abstract String getRegNumber();
    public abstract String getType();
    public abstract double getLength();
    public abstract User getOwner();

}
