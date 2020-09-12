package model.roles;

import model.Fee;
import model.boats.Boat;
import model.Login;

import java.time.LocalDate;
import java.util.ArrayList;

public class Secretary extends Users {
    LocalDate currentdate = LocalDate.now();
    private Login credentials;
    private String firstName;
    private String surName;
    private String socialNumber;
    private int age;
    private int birthMonth;


    public Secretary(String firstName, String surName, String socialNumber, String password) {
        this.surName = surName;
        this.firstName = firstName;
        this.socialNumber = socialNumber;
        // TODO: 2020-09-08 implement 
        setAge();
        setMonth();
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

    @Override
    public void removeBoat(Boat boat) {
    }

    @Override
    public void addBoat(Boat boat){
    }

    @Override
    public String getUserType() {
        return "Secretary";
    }

    @Override
    public void setAge() {
        int currentYear = currentdate.getYear();
        this.age = currentYear - Integer.valueOf(this.socialNumber.substring(0,3));

    }

    @Override
    public void setMonth() {
        int currentMonth = currentdate.getMonthValue();
        this.birthMonth = currentMonth - Integer.valueOf(this.socialNumber.substring(4,5));
    }

    @Override
    public int getAge() {
        return this.age;
    }

    @Override
    public int getMonth() {
        return this.birthMonth;
    }

    @Override
    public Boat[] returnBoats() {
        return null;
    }


}
