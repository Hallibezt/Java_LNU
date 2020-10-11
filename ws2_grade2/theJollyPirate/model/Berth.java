package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Berth implements Serializable {
    private static final long serialVersionUID = -3564668532112937369L; //For the serializer, if it is not set then i can happen that ID do not match
    private int location;
    private Boat boat = null;
    private Member currentMember = null;
    private ArrayList<Member> previousMembers = new ArrayList<>();

    public void setLocation(int i) {
        location = i;
    }

    public int getLocation() {return location;}

    public Boat getBoat(){ return this.boat; }

    public Member getCurrentMember() {
        return currentMember;
    }

    public void addBoat(Boat boat, Member owner){
        currentMember = owner;
        this.boat = boat;
    }

    public void removeBoat(){
        boat = null;
        //Add to the list of previous users
        if (previousMembers.size() == 0 ){
            previousMembers.add(currentMember);
        }
        else { //If not empty, then check if the member is alreay in it, if not; then add.
            for (int i = 0; i < previousMembers.size(); i++) {
                if (previousMembers.get(i).getSocialNumber() == currentMember.getSocialNumber()) {
                    break; //Already there so just return
                }
                else
                    previousMembers.add(currentMember); //not in the list, so add.
            }
        }
        currentMember = null;
    }

    //Method to see if this is a preferable berth to allocate to this member's boat, since it has used it before
    public boolean hasRentedBert(Member member){
        if (previousMembers.size() != 0 ){ //the list is not empty
            for (Member previousUser : previousMembers) {
                if (member.getSocialNumber() == previousUser.getSocialNumber()){
                    return true;
                }
            }
        }
        return false;
    }

}
