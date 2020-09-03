package model.roles;
import model.boats.Boat;
import model.Fee;
import model.Login;

import java.util.ArrayList;

public class Member extends Users {
    private Login credentials;
    private String firstName;
    private String surName;
    private String socialNumber;
    private ArrayList<Boat> boats = new ArrayList<>();
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

    @Override
    public void removeBoat(Boat boat) {
        for(int i = 0; i<boats.size(); i++){
            if(boats.get(i).getRegNumber().equalsIgnoreCase(boat.getRegNumber()))
                boats.remove(i);
        }
    }

    @Override
    public void addBoat(Boat boat){
        this.boats.add(boat);
    }

    @Override
    public String getUserType() {
        return "Member";
    }

    @Override
    public int getAge() {
        return 0;
    }


    public Boat[] returnBoats(){
        Boat[] temp = new Boat[boats.size()];
        for(int i = 0; i<boats.size(); i++){
            temp[i] = boats.get(i);
        }
        return temp;
    }

    public Fee getFee(){return this.fee;}




//private ArrayList<Event> events


}
