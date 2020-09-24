package model;

import java.io.Serializable;
import java.util.Random;

public class Login implements Serializable {
    private String userID;
    private String password;



    public Login() {
    }

    public Login(String userID, String password) {
        addPassword(password);
        addUserID(userID);
    }

    public boolean compareTo(Login passed){
        return passed.getUserID().equalsIgnoreCase(this.userID) & passed.getPassword().equals(this.password);
    }

    public void addLoginUserID(String input) {
        this.userID = input;
    }

    public void addUserID(String input) {
        Random number = new Random();
        this.userID = input + number.nextInt(1000);
    }

    //If so unlikely that two of the members have the same head char in firstname, the same surname and get the same random number
    public void changeUserID() {
        Random number = new Random();
        this.userID = this.userID + number.nextInt(100);
    }

    public void addPassword(String input) {
        this.password = input;
    }

    public String getUserID(){
        return userID;
    }
    public String getPassword(){
        return password;
    }
}
