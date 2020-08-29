package model.roles;

import model.Login;

public class Secretary extends Users {
    private Login credentials;
    private String name;
    private int socialNumber;


    @Override
    public Login getLogin() {
        return null;
    }

    @Override
    public String getFullName() {
        return null;
    }

    @Override
    public String getSocialNumber() {
        return null;
    }

    @Override
    public void addLogin(String password) {

    }

    @Override
    public void addFirstName(String firstName) {

    }

    @Override
    public void addSurName(String surName) {

    }
}
