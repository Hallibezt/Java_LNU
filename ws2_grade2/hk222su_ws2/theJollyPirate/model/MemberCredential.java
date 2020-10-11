package model;

import java.io.Serializable;
import java.util.Random;

public class MemberCredential implements Serializable {
    private String userID;
    private String password;
    private final Long socialNumber;

    public MemberCredential( String password, Long socialNumber) {
       this.password = password;
       this.socialNumber = socialNumber;
    }

    public String getUserID(){
        return userID;
    }
    public String getPassword(){
        return password;
    }
    public Long getSocialNumber(){return socialNumber;}

    public void updatePassword(String input) {
        this.password = input;
    }

    public boolean matchCredentials(String userID, String password){
        if(this.userID.equalsIgnoreCase(userID) && this.password.equals(password))
            return true;
        else
            return false;
    }

    public String createUserID(String name) {
        String firstname = name.split(" ")[0]; //just get the first name
        Random number = new Random();
        int randInt = number.nextInt(1000); //Hopefully not more than 10000
        String theID = firstname + "_" + randInt;
        this.userID = theID;
        return theID;
    }

}
