package model;

import model.boats.Boat;
import model.roles.Users;

import java.io.Serializable;
import java.util.ArrayList;

public class Berths implements Serializable {
    private int location;
    private Boat boat = null;
    private Users currentUser = null;
    private ArrayList<Users> previousUsers = new ArrayList<>();

    public void setLocation(int i) {
        location = i;
    }

    public Users getCurrentUser() {
        return currentUser;
    }

    public void addBoat(Boat boat){
            currentUser = boat.getOwner();
            this.boat = boat;
    }

    public Boat getBoat(){
        return this.boat;
    }

    public void removeBoat(){
        boat = null;
        if (previousUsers == null ){
            ArrayList<Users> temp = new ArrayList<>();
            temp.add(currentUser);
            this.previousUsers = temp;}
        else {
            for (int i = 0; i < previousUsers.size(); i++) {
                if (previousUsers.get(i).getSocialNumber().equalsIgnoreCase(currentUser.getSocialNumber())) {
                    break;
                } else
                    previousUsers.add(currentUser);
            }
        }
        currentUser = null;

    }

    public boolean hasRentedBert(Users user){
        if (previousUsers != null ){
        for(int i = 0; i<previousUsers.size(); i++){
            if(user.getSocialNumber().equalsIgnoreCase(previousUsers.get(i).getSocialNumber())){
                return true;
            }
        }}
        return false;
    }

    public int getLocation() {
        return location;
    }
}
