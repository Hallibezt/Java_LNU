package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Berth implements Serializable {
    private static final long serialVersionUID = -3564668532112937369L;
    private int location;
    private Boat boat = null;
    private Member currentUser = null;
    private ArrayList<Member> previousUsers = new ArrayList<>();

    public void setLocation(int i) {
        location = i;
    }

    public Member getCurrentUser() {
        return currentUser;
    }

    public void addBoat(Boat boat, Member owner){
            currentUser = owner;
            this.boat = boat;
    }

    public Boat getBoat(){
        return this.boat;
    }

    public void removeBoat(){
        boat = null;
        if (previousUsers == null ){
            ArrayList<Member> temp = new ArrayList<>();
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

    public boolean hasRentedBert(Member user){
        if (previousUsers != null ){
            for (Member previousUser : previousUsers) {
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
