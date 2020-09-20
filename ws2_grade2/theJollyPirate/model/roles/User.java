package model.roles;

import model.Fee;
import model.Price;
import model.boats.Boat;
import model.Login;

import java.io.Serializable;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 4442257940559189557L;

    public abstract Login getLogin();
    public abstract String getFullName();
    public abstract String getSocialNumber();
    public abstract void addLogin(String password); //for higher grade part
    public abstract void addFirstName(String firstname);
    public abstract void addSurName(String surName);
    public abstract void removeBoat(Boat boat);
    public abstract void addBoat(Boat boat, Price price);
    public abstract String getUserType();
    public abstract void setAge();
    public abstract void setMonth();
    public abstract int getAge(); //for higher grade part
    public abstract int getMonth(); //for higher grade part
    public abstract Boat[] returnBoats();
    public abstract Fee getFee();
    public abstract void updateBoat(Boat boat, Price price);
}
