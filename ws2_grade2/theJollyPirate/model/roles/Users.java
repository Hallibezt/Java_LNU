package model.roles;

import model.boats.Boat;
import model.Login;

import java.io.Serializable;

public abstract class Users implements Serializable {

    public abstract Login getLogin();
    public abstract String getFullName();
    public abstract String getSocialNumber();
    public abstract void addLogin(String password);
    public abstract void addFirstName(String firstname);
    public abstract void addSurName(String surName);
    public abstract void removeBoat(Boat boat);
    public abstract void addBoat(Boat boat);
    public abstract String getUserType();
    public abstract void setAge();
    public abstract void setMonth();
    public abstract int getAge();
    public abstract int getMonth();
    public abstract Boat[] returnBoats();
}
