package model.roles;
import model.Boat;
import model.Fee;
import model.Login;

import java.util.ArrayList;

public class Member extends Users {
    private Login credentials;
    private String firstName;
    private String surName;
    private String socialNumber;
    private ArrayList<Boat> boats;
    private Fee fee = new Fee();

    public Member(){}

    public Member(String firstName, String surName, String socialNumber, String password){
        this.surName = surName;
        this.firstName = firstName;
        this.socialNumber = socialNumber;
        credentials = new Login(firstName.substring(0,1)+"." + surName + "_", password);
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

    @Override
    public void addLogin(String password) {
        credentials = new Login(firstName.substring(0,1)+"." + surName + "_", password);    }

    @Override
    public void addFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public void addSurName(String surname) {
       this.surName = surname;
    }

    public void addBoat(Boat boat){
        this.boats.add(boat);
    }

    public Boat[] returnBoats(){
        if(boats.isEmpty())
            throw new NullPointerException();
        return (Boat[]) boats.toArray();
    }

    public Fee getFee(){return this.fee;}

    public int numberBoats(){return boats.size();}


//private ArrayList<Event> events


}
