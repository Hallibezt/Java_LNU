package model.boats;

import model.Berths;
import model.roles.Users;

public abstract class Boat {
    public abstract void changeRegNumber(String registrationNumber);
    public abstract void changeLength(double length);
    public abstract void changeOwner(Users user);
    public abstract void addLocation(int location);
    public abstract int getLoacation();
    public abstract String getRegNumber();
    public abstract String getType();
    public abstract double getLength();
    public abstract Users getOwner();

}
