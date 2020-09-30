package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public  class Member implements Serializable {

    private static final long serialVersionUID = -5515181500783304862L;
    private final LocalDate currentDate = LocalDate.now();
    private final Login credentials;
    private String firstName;
    private String surName;
    private final String socialNumber;
    private final ArrayList<Boat> boats = new ArrayList<>();
    private final Fee fee = new Fee();
    private int age; //WS3 attribute
    private int birthMonth; //WS3 attribute



    public Member(String firstName, String surName, String socialNumber, String password) {
        this.surName = surName;
        this.firstName = firstName;
        this.socialNumber = socialNumber;
        setAge();
        setMonth();
        credentials = new Login(firstName.charAt(0)+"." + surName + "_", password);
    }


    public Login getLogin() {
        return credentials;
    }

    public String getFullName() {
        return firstName + " " + surName;
    }


    public String getSocialNumber() {
        return socialNumber;
    }


   // public void addLogin(String password) { WS3 method
     //   credentials = new Login(firstName.charAt(0)+"." + surName + "_", password);    }


    public void addFirstName(String firstName)  {
        this.firstName = firstName;
    }


    public void addSurName(String surname) {
        this.surName = surname;
    }


    public void removeBoat(Boat boat) {
        for(int i = 0; i<boats.size(); i++){
            if(boats.get(i).getRegNumber().equalsIgnoreCase(boat.getRegNumber()))
                boats.remove(i);
        }
    }

    public void addBoat(Boat boat){
        this.boats.add(boat);
        fee.addBoatFee(boat.getPrice());
    }

    public void setAge() {
        int currentYear = currentDate.getYear();
        this.age = currentYear - Integer.parseInt(this.socialNumber.substring(0,3));

    }


    public void setMonth() {
        int currentMonth = currentDate.getMonthValue();
        this.birthMonth = currentMonth - Integer.parseInt(this.socialNumber.substring(4,5));
    }


    //public int getAge() { WS3 method
        //return this.age;
   // }


    //public int getMonth() { WS3 method
    //    return this.birthMonth;
    //}

    public void updateBoat(Boat boat) {
        for (int i = 0; i < boats.size(); i++) {
            if (boats.get(i).getRegNumber().equals(boat.getRegNumber())) {
                boats.remove(i);
                boats.add(boat);
                break;
            }
        }
        fee.addBoatFee(boat.getPrice());

    }
    public Boat[] returnBoats(){
        Boat[] temp = new Boat[boats.size()];
        for(int i = 0; i<boats.size(); i++){
            temp[i] = boats.get(i);
        }
        return temp;
    }

    public Fee getFee(){return this.fee;}


}
