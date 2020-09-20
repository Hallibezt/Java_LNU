package model.roles;
import model.Price;
import model.boats.Boat;
import model.Fee;
import model.Login;

import java.time.LocalDate;
import java.util.ArrayList;

public class Member extends User {
    private static final long serialVersionUID = -5515181500783304862L;
    private final LocalDate currentDate = LocalDate.now();
    private Login credentials;
    private String firstName;
    private String surName;
    private String socialNumber;
    private final ArrayList<Boat> boats = new ArrayList<>();
    private final Fee fee = new Fee();
    private int age;
    private int birthMonth;

    public Member(){}

    public Member(String firstName, String surName, String socialNumber, String password){
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
        for(int i = 0; i<boats.size(); i++){
            if(boats.get(i).getRegNumber().equalsIgnoreCase(boat.getRegNumber()))
                boats.remove(i);
        }
    }

    public void addBoat(Boat boat, Price price){
        this.boats.add(boat);
        fee.addBoatFee(price);
    }

    @Override
    public String getUserType() {
        return "Member";
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

    public void updateBoat(Boat boat, Price price) {
        for (int i = 0; i < boats.size(); i++) {
            if (boats.get(i).getRegNumber().equals(boat.getRegNumber())) {
                boats.remove(i);
                boats.add(boat);
                break;
            }
        }
        fee.addBoatFee(price);

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
