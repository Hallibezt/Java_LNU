package model.boats;

import model.Berths;
import model.roles.Users;

import java.io.Serializable;

public abstract class Boat implements Serializable {


    public abstract void changeLength(double length);
    public abstract void addLocation(int location);
    public abstract int getLoacation();
    public abstract String getRegNumber();
    public abstract String getType();
    public abstract double getLength();
    public abstract Users getOwner();

}
