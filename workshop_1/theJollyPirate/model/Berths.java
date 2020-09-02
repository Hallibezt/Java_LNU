package model;

import model.roles.Member;
import model.roles.Users;

import java.util.ArrayList;

public class Berths {
    private int location;
    private Boat boat = null;
    private Users currentUser = null;
    private ArrayList<Users> previousUsers;

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
        currentUser.removeBoat(boat);
        boat = null;
        for(int i = 0; i<previousUsers.size(); i++){
            if(previousUsers.get(i).getSocialNumber().equalsIgnoreCase(currentUser.getSocialNumber())){
                break;
            }
            else
                previousUsers.add(currentUser);
        }
        currentUser = null;

    }

    public boolean hasRentedBert(Users user){
        for(int i = 0; i<previousUsers.size(); i++){
            if(user.getSocialNumber().equalsIgnoreCase(previousUsers.get(i).getSocialNumber())){
                return true;
            }
        }


        return false;
    }
}
