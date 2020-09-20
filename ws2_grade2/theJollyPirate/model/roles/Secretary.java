package model.roles;

import model.Fee;
import model.Price;
import model.boats.Boat;
import model.Login;

import java.time.LocalDate;

public class Secretary extends User {
    private static final long serialVersionUID = 7678778911016239390L;
    LocalDate currentDate = LocalDate.now();
    private Login credentials;
    private String firstName;
    private String surName;
    private final String socialNumber;
    private int age;
    private int birthMonth;


    public Secretary(String firstName, String surName, String socialNumber, String password) {
        this.surName = surName;
        this.firstName = firstName;
        this.socialNumber = socialNumber;
        setAge();
        setMonth();
        credentials = new Login(firstName.charAt(0)+"." + surName + "_", password);
    }


    @Override
    public Login getLogin() {
        return credentials;
    }

    @Override
    public String getFullName() {
        return firstName + " " + surName;
    }

    @Override
    public String getSocialNumber() {
        return socialNumber;
    }

    @Override
    public void addLogin(String password) {
        credentials = new Login(firstName.charAt(0)+"." + surName + "_", password);    }

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
    public void addBoat(Boat boat, Price price){
    }

    @Override
    public String getUserType() {
        return "Secretary";
    }

    @Override
    public void setAge() {
        int currentYear = currentDate.getYear();
        this.age = currentYear - Integer.parseInt(this.socialNumber.substring(0,3));

    }

    @Override
    public void setMonth() {
        int currentMonth = currentDate.getMonthValue();
        this.birthMonth = currentMonth - Integer.parseInt(this.socialNumber.substring(4,5));
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

    public void updateBoat(Boat boat, Price price) {    }


    @Override
    public Fee getFee() {
        return null;
    }


}
