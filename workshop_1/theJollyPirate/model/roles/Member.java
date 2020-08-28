package model.roles;
import model.Boat;
import model.Login;

import java.util.ArrayList;

public class Member extends Users {
    private Login credentials;
    private String firstName;
    private String surName;
    private String socialNumber;
    private ArrayList<Boat> boats;

    public Member(String firstName, String surName, String socialNumber, String password){
        this.surName = surName;
        this.firstName = firstName;
        this.socialNumber = socialNumber;
        credentials = new Login(firstName.substring(0,1)+"_" + surName + "_", password);
    }

    @Override
    public Login getLogin() {
        return credentials;
    }

    @Override
    public String getFullName() {
        String fullName = firstName + " " + surName;
        return fullName;
    }

    @Override
    public String getSocialNumber() {
        return socialNumber;
    }
//private ArrayList<Event> events


}