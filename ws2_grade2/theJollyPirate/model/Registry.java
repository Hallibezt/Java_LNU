package model;


import java.io.Serializable;
import java.util.ArrayList;


public class Registry implements Serializable {
    private static final long serialVersionUID = -7013385061015921422L; //For the serializer, if it is not set then i can happen that ID do not match
    private ArrayList<Member> registeredMembers = new ArrayList<>(); //Everyone is registered with password but only secretary can get the full login options, members registering is for them to handle event booking in the future
    private final Berth[] berths = new Berth[200];


    public Registry()  {
        hardcodedFirstTimeUse();
    }

    //When the program was started the first time at Jolly Pirate the berths got address/location and the secretary was registered
    private void hardcodedFirstTimeUse()  {
        for(int i = 0; i < 200; i++){
            berths[i] = new Berth();
            berths[i].setLocation(i+1);
        }
    }

    public boolean addMember(Member member)  {
        boolean inRegistry = false;
        if(registeredMembers.size() == 0) {//for the first time so we do not get stuck in forever loop
            giveMemberUserID(member); //the member gets assigned userName before being registered
            registeredMembers.add(member);
            return true;
        }
        else {
            for (Member regMember : registeredMembers) {
                if (member.getSocialNumber() == regMember.getSocialNumber())
                    inRegistry = true;
            }
            if(!inRegistry){
                giveMemberUserID(member); //the member gets assigned userName before being registered
                registeredMembers.add(member);
                return true;
            }
            else
                return false;
        }
    }

    //Called from addMember, if not in registry - assign them member an userID
    private void giveMemberUserID(Member member) {
        boolean uniqueID = false;
        String userID = null;
        while(!uniqueID) {
            userID = member.getCredentials().createUserID(member.getFullName());
            if (registeredMembers.size() == 0) {//for the first time so we do not get stuck in forever loop
                uniqueID = true;
            }
            else {
                for (Member regMember : registeredMembers) {
                    if(regMember.getUserID().equals(userID))
                        break;
                }
                uniqueID = true;
            }
        }
    }

    //Removes a member and frees all berths that has member's boats parked.
    public void removeMember(Member member) {
        for(int i = 0; i< registeredMembers.size(); i++){
            if(registeredMembers.get(i).getUserID().equals(member.getUserID())){
                registeredMembers.remove(i); //Member found and removed
                //Now clear all his boats from the berths
                for (Berth berth : berths) {
                    if (berth.getBoat() != null) {
                        if (berth.getCurrentMember().getUserID().equals(member.getUserID())) {
                            berth.removeBoat();
                        }
                    }
                }
            }
        }
    }

    public void updateMember(Member member){
        for(int i = 0; i< registeredMembers.size(); i++){
            if(registeredMembers.get(i).getUserID().equalsIgnoreCase(member.getUserID())){
                registeredMembers.set(i, member);
            }
        }
    }

    public Member findMember(String credential, String credential1) {
        Member member = null;
        if(credential1 == null){ //We are just searching for a member by userID, like option 4 in main menu
            for (Member regUser : registeredMembers) {
                if (regUser.getUserID().equalsIgnoreCase(credential)) {
                    member = regUser;
                }
            }
        }
        else { //We search by username and password
            for (Member regUser : registeredMembers) {
                if (regUser.getCredentials().matchCredentials(credential, credential1)) {
                    member = regUser;
                }
            }
        }
        return member;
    }

    public boolean availableBerth(){ //See if there are any available berths
        boolean available = false;//2
        for (Berth berthMember : berths) {
            //If there is an available berth then its currentMember would be null
            if (berthMember.getCurrentMember() == null) {
                available = true;
                break;
            }
        }
        return available;
    }

    //See if given boat registration number is in the registry
    public boolean checkRegNumber(String regNumber) {
        for (Berth berth : berths) {
            if (berth.getBoat() != null) {
                if (berth.getBoat().getRegNumber().equals(regNumber))
                    return true;
            }
        }
        return false;
    }

    public Berth findPreviouslyUsedBerth(Member member){
        //Do not worry about this causing null pointer,
        //because there has been checked before if there are any available berths
        //- so it will always be assigned one in the end
        Berth availableBerth = null;
        for (Berth berth : berths) {
            //Has to be available
            if (berth.getCurrentMember() == null) {
                //Found one - then return that one
                if (berth.hasRentedBert(member)) {
                    availableBerth = berth;
                    return availableBerth;
                }
                //Else return one available if he has no previous one
                else
                    availableBerth = berth;
            }
        }
        //Nothing found then null is returned
        return availableBerth;
    }

    public void updateBerths(int location, Boat boat, Member owner){
        berths[location-1].addBoat(boat, owner);
    }

    public Boat findBoat(String boatRegistrationNumber) {
        Boat boat;
        for (Berth berth : berths) {
            if (berth.getBoat() != null) {
                if (berth.getBoat().getRegNumber().equals(boatRegistrationNumber)) {
                    boat = berth.getBoat();
                    return boat;
                }
            }
        }
        //No boat found
        return null;
    }

    //Return copy of the members list to print info
    public Member[] returnMembers(){
        Member[] members = new Member[registeredMembers.size()];
        for(int i = 0; i< registeredMembers.size(); i++){
            members[i] = registeredMembers.get(i);
        }
        return members;
    }

    //Updates the boat in the boat club's berth list and in boat club's member list
    public void updateBoat(Boat boat){
        for (Berth berth : berths) {
            //Enough compare the location, should not change when boat is updated
            if (berth.getLocation() == boat.getLocation()){
                Member boatOwner = berth.getCurrentMember();
                //Enough to use berths add() method, it overrides the old boat with the updated and same owner
                berth.addBoat(boat, boatOwner);
                //Update the boat also at the members side
                for (Member regUser : registeredMembers) {
                    if (regUser.getUserID().equalsIgnoreCase(boatOwner.getUserID()))
                        regUser.updateBoat(boat);
                }
            }
        }

    }

    //Removes boat both from berth list and member list in the boat club
    public void removeBoat(Boat boat, Member owner) {
        for (Berth berth : berths) {
            if (berth.getBoat() != null) {
                if (berth.getBoat().getRegNumber().equals(boat.getRegNumber())) {
                    berth.removeBoat();
                }
            }
        }
        for (Member regUser : registeredMembers) {
            if (regUser.getUserID().equalsIgnoreCase(owner.getUserID()))
                regUser.removeBoat(boat);
        }
    }
}

