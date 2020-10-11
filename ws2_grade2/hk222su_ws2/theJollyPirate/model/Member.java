package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public  class Member implements Serializable {

    private static final long serialVersionUID = -5515181500783304862L; //For the serializer, if it is not set then i can happen that ID do not match
    private final LocalDate currentDate = LocalDate.now();
    private final MemberCredential credentials;
    private String firstName;
    private String surName;
    private final ArrayList<Boat> boats = new ArrayList<>();
    private final Fee fee = new Fee();
    private int age; //WS3 attribute
    private int birthMonth; //WS3 attribute



    public Member(String firstName, String surName, String socialNumber, String password) {
        this.surName = surName;
        this.firstName = firstName;
        setAge((int) (Long.parseLong(socialNumber)%100000000)); //send just the year
        setMonth((int) (Long.parseLong(socialNumber)%1000000)); //send year and month
        credentials = new MemberCredential(password, Long.parseLong(socialNumber));
    }

    public String getFullName() {
        return firstName + " " + surName;
    }
    public String getUserID(){return this.credentials.getUserID();}
    public String getPassword(){return this.credentials.getPassword();}
    public Long getSocialNumber() {return this.credentials.getSocialNumber();}
    public Fee getFee(){return this.fee;}
    public MemberCredential getCredentials(){return this.credentials;}

    public void updateFirstName(String firstName)  {
        this.firstName = firstName;
    }
    public void updateSurName(String surname) {
        this.surName = surname;
    }

    public void addBoat(Boat boat){
        this.boats.add(boat);
        fee.addBoatFee(boat.getPriceObject());
    }

    public void removeBoat(Boat boat) {
        for(int i = 0; i<boats.size(); i++){
            if(boats.get(i).getRegNumber().equalsIgnoreCase(boat.getRegNumber()))
                boats.remove(i);
        }
    }

    public void updateBoat(Boat boat) {
        for (int i = 0; i < boats.size(); i++) {
            if (boats.get(i).getRegNumber().equals(boat.getRegNumber())) {
                boats.remove(i);
                boats.add(boat);
                break;
            }
        }
        fee.addBoatFee(boat.getPriceObject());
    }

    public Boat[] getMemberBoats(){ //read to array to protect the list attribute
        Boat[] temp = new Boat[boats.size()];
        for(int i = 0; i<boats.size(); i++){
            temp[i] = boats.get(i);
        }
        return temp;
    }

    public void setAge(int year) {
        int currentYear = currentDate.getYear();
        this.age = currentYear - year;
    }

    public void setMonth(int yearAndMonth) {
        //Extract just the month so i.e. 198410 - 198400 == 10
        this.birthMonth = yearAndMonth - ((yearAndMonth%100)*100);
    }

    public boolean ownsBoat(Boat boat) {
        for (Boat boat1 : boats) {
            if (boat.getRegNumber().equals(boat1.getRegNumber()))
                return true;
        }
        return false;
    }


    //public int getAge() { WS3 method
        //return this.age;
   // }


    //public int getMonth() { WS3 method
    //    return this.birthMonth;
    //}






}
