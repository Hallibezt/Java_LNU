package model;

import model.roles.Users;

import java.util.ArrayList;

public class Berths {
    int location;
    String boatRegNumber = null;
    Users currentUser = null;
    ArrayList<Users> previousUsers;

    public void setLocation(int i) {
        location = i;
    }

    public void addBoat(Users user, String boatRegNumber){
        if (this.currentUser == null){
            currentUser = user;
            this.boatRegNumber = boatRegNumber;}
        else
            System.out.println("Being used");
    }

    public void removeBoat(Users users){
        //boatRegNumber sett to null
        //Current user moved to previousUsers and set to Null
    }
}
