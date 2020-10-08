package model;

import model.exceptions_errors.BoatNotFoundException;
import model.exceptions_errors.CreditFailureException;
import java.io.Serializable;
import java.util.ArrayList;


public class Registry implements Serializable {
    private ArrayList<Member> regUsers = new ArrayList<>(); //Everyone is registered with password but only secretary can get the full login options, members registering is for them to handle event booking in the future
    private final Berth[] berths = new Berth[200];
    private static final long serialVersionUID = -7013385061015921422L;






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

    public String addMember(Member member) {
        boolean sameID = true;
        String memberUserName = member.getLogin().getUserID();
        while(sameID){
            if(regUsers.size() == 0)//for the first time so we do not get stuck in forever loop
                sameID = false;

            for (Member regUser : regUsers) {
                if (member.getSocialNumber().equals(regUser.getSocialNumber()))
                    throw new IllegalArgumentException();
                if (member.getLogin().getUserID().equalsIgnoreCase(regUser.getLogin().getUserID())) {
                    member.getLogin().changeUserID();
                    sameID = true;
                    break;
                }
                else {
                    sameID = false;
                }
            }
        }
        regUsers.add(member);
        return memberUserName;
    }


    public void removeMember(Member member) { //Removes a member and frees all berths that has member's boats parked.
            for(int i =0; i<regUsers.size();i++){
                if(regUsers.get(i).getLogin().compareTo(member.getLogin())){
                    regUsers.remove(i);
                    for (Berth berth : berths) {
                        if (berth.getBoat() != null) {
                            if (berth.getCurrentUser().getLogin().compareTo(member.getLogin())) {
                                berth.removeBoat();
                            }
                        }
                    }
                }
            }
    }

    public void updateMember(Member member){
        ArrayList<Member> temp = regUsers;
        for(int i = 0; i< temp.size(); i++){
            if(temp.get(i).getLogin().getUserID().equalsIgnoreCase(member.getLogin().getUserID())){
                temp.set(i, member);
            }
        }
        regUsers = temp;
            }

    public Member[] returnMembers(){ //Return all members - exclude secretary or treasury
        Member[] members = new Member[regUsers.size()];
            for(int i = 0; i<regUsers.size(); i++){
                members[i] = regUsers.get(i);
            }

        return members;
    }

    private boolean confirmMember(Login givenLogin){

        for (Member member : regUsers) {
            if (member.getLogin().compareTo(givenLogin)) {
                return true;
            }
        }
        return false;
    }

    public Member returnOneMember(Login login) throws CreditFailureException {
        if(!confirmMember(login))
            throw new CreditFailureException("");
        Member member = null;
        for (Member regUser : regUsers) {
            if (regUser.getLogin().compareTo(login)) {
                member = regUser;
            }
        }
        return member;
    }

    public boolean availableBerth(){ //See if there are any available berths
        boolean b = false;//2
        Berth[] list = returnBerths();
        for (Berth value : list) {
            if (value.getCurrentUser() == null) {
                b = true;
                break;
            }
        }
        return b;
    }

    public Berth[] returnBerths(){
        return berths;
    }

    public boolean checkRegNumber(String regNumber) { //See if given registration number is in the database
        for (Berth berth : berths) {
            if (berth.getBoat() != null) {
                if (berth.getBoat().getRegNumber().equals(regNumber))
                    return true;
            }
        }
        return false;
    }

    public void updateBerths(int location, Boat boat, Member owner){
        berths[location-1].addBoat(boat, owner);
    }


    public Berth findBert(Member member){
        Berth[] list = returnBerths();
        Berth berth = list[0];
        for (Berth value : list) {
            if (value.getCurrentUser() == null) {
                if (value.hasRentedBert(member)) {
                    berth = value;
                    return berth;
                } else
                    berth = value;
            }
        }
        return berth;
    }

    public void updateBoat(Boat boat ){    //changeOwner() update berth and ownerList changeType() - update berth, owner and possible fee changeLength() update berth, owner and possible fee
        for (Berth berth : berths) {
            if (berth.getLocation() == boat.getLocation()){
                Member boatOwner = berth.getCurrentUser();
                berth.addBoat(boat, boatOwner);
                for (Member regUser : regUsers) {
                    if (regUser.getLogin().compareTo(boatOwner.getLogin()))
                        regUser.updateBoat(boat);
                }
            }
        }

    }


    public Boat findBoat(String boatRegistrationNumber, Member member) throws BoatNotFoundException {
        Boat boat;
        for (Berth berth : berths) {
            if (berth.getBoat() != null) {
                if (berth.getBoat().getRegNumber().equals(boatRegistrationNumber) & berth.getCurrentUser().getLogin().compareTo(member.getLogin())) {
                    boat = berth.getBoat();
                    return boat;
                }
            }
        }
        throw new BoatNotFoundException("");
    }

    public void removeBoat(Boat boat, Member owner) {
        for (Berth berth : berths) {
            if (berth.getBoat() != null) {
                if (berth.getBoat().getRegNumber().equals(boat.getRegNumber())) {
                    berth.removeBoat();
                }
            }
        }
        for (Member regUser : regUsers) {
            if (regUser.getLogin().compareTo(owner.getLogin()))
                regUser.removeBoat(boat);
        }

    }
}

