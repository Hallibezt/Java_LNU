package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Berth implements Serializable {
    private static final long serialVersionUID = -3564668532112937369L;
    private int location;
    private Boat boat = null;
    private User currentUser = null;
    private ArrayList<User> previousUsers = new ArrayList<>();

    public void setLocation(int i) {
        location = i;
    }

    public User getCurrentUser() {
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
            ArrayList<User> temp = new ArrayList<>();
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

    public boolean hasRentedBert(User user){
        if (previousUsers != null ){
            for (User previousUser : previousUsers) {
                if (user.getSocialNumber().equalsIgnoreCase(previousUser.getSocialNumber())) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getLocation() {
        return location;
    }
}
