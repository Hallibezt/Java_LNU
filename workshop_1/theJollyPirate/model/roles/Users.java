package model.roles;

import model.Boat;
import model.Login;

public abstract class Users {

    public abstract Login getLogin();
    public abstract String getFullName();
    public abstract String getSocialNumber();
    public abstract void addLogin(String password);
    public abstract void addFirstName(String firstname);
    public abstract void addSurName(String surName);
    public abstract void removeBoat(Boat boat);
}
