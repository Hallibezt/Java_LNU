package model.roles;
import model.boats.Boat;
import model.Fee;
import model.Login;

import java.time.LocalDate;
import java.util.ArrayList;

public class Member extends Users {
    LocalDate currentdate = LocalDate.now();
    private Login credentials;
    private String firstName;
    private String surName;
    private String socialNumber;
    private ArrayList<Boat> boats = new ArrayList<>();
    private Fee fee = new Fee();
    private int age;
    private int birthMonth;

    public Member(){}

    public Member(String firstName, String surName, String socialNumber, String password){
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
        for(int i = 0; i<boats.size(); i++){
            if(boats.get(i).getRegNumber().equalsIgnoreCase(boat.getRegNumber()))
                boats.remove(i);
        }
    }

    @Override
    public void addBoat(Boat boat){
        this.boats.add(boat);
        fee.addBoatFee(boat);
    }

    @Override
    public String getUserType() {
        return "Member";
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

    public void updateBoat(Boat boat) {
        Boat prevBoat = null;
        String prevType = null;
        double prevLength = 0;
        for (int i = 0; i < boats.size(); i++) {
            if (boats.get(i).getRegNumber().equals(boat.getRegNumber())) {
                prevBoat = boats.get(i);
                boats.remove(i);
                boats.add(boat);
                break;
            }
        }
        //Now prepare to update fee if needed, i.e. changed the type or length you have to pay the difference
        prevType = prevBoat.getType();
        prevLength = prevBoat.getLength();
        fee.updateFee(boat, prevType, prevLength);

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
