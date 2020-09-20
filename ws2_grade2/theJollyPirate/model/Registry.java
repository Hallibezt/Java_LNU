package model;

import controller.exceptions_errors.BoatNotFoundException;
import model.boats.Boat;
import model.roles.Secretary;
import model.roles.User;

import java.io.Serializable;
import java.util.ArrayList;


public class Registry implements Serializable {
    private ArrayList<User> regUsers = new ArrayList<>(); //Everyone is registered with password but only secretary can get the full login options, members registering is for them to handle event booking in the future
    private final Berth[] berths = new Berth[200];
    private static final long serialVersionUID = -7013385061015921422L;




    public Registry() {
        hardcodedFirstTimeUse();
    }

    //When the program was started the first time at Jolly Pirate the berths got address/location and the secretary was registered
    private void hardcodedFirstTimeUse(){
        //Secretary is more for grade4 part
        User theSecretary = new Secretary("Haraldur", "Kristjansson", "198410249999", "IceHot1");
        regUsers.add(theSecretary);
        for(int i = 0; i < 200; i++){
            berths[i] = new Berth();
            berths[i].setLocation(i+1);
        }
    }



    public String addMember(User member) {
        boolean sameID = true;
        String memberUserName = member.getLogin().getUserID();
        while(sameID){
            for (User regUser : regUsers) {
                if (member.getSocialNumber().equals(regUser.getSocialNumber()))
                    throw new IllegalArgumentException();
                if (member.getLogin().getUserID().equals(regUser.getLogin().getUserID())) {
                    member.getLogin().changeUserID();
                    break;
                } else {
                    sameID = false;
                }
            }
        }

        regUsers.add(member);
        return memberUserName;
    }


    public void removeMember(User member) { //Removes a member and frees all berths that has member's boats parked.
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

    public void updateMember(User member){
        ArrayList<User> temp = regUsers;
        for(int i = 0; i< temp.size(); i++){
            if(temp.get(i).getLogin().getUserID().equalsIgnoreCase(member.getLogin().getUserID())){
                temp.set(i, member);
            }
        }
        regUsers = temp;
            }

    public User[] returnMembers(){ //Return all members - exclude secretary or treasury
        ArrayList<User> temp = new ArrayList<>();
        for (User regUser : regUsers) {
            if (regUser.getUserType().equalsIgnoreCase("Member")) {
                temp.add(regUser);
            }
        }
        User[] members = new User[temp.size()];
            for(int i = 0; i<temp.size(); i++){
                members[i] = temp.get(i);
            }

        return members;
    }

    public User returnOneMember(Login login){
        User member = null;
        for (User regUser : regUsers) {
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

    public void updateBerths(int location, Boat boat){
        berths[location-1].addBoat(boat);
    }


    public Berth findBert(User member){
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

    public void updateBoat(Boat boat, Price price ){    //changeOwner() update berth and ownerList changeType() - update berth, owner and possible fee changeLength() update berth, owner and possible fee
        for (Berth berth : berths) {
            if (berth.getLocation() == boat.getLocation())
                berth.addBoat(boat);
        }
        for (User regUser : regUsers) {
            if (regUser.getLogin().compareTo(boat.getOwner().getLogin()))
                regUser.updateBoat(boat, price);
        }

    }


    public Boat findBoat(String boatRegistrationNumber, User member) throws BoatNotFoundException {
        Boat boat;
        for (Berth berth : berths) {
            if (berth.getBoat() != null) {
                if (berth.getBoat().getRegNumber().equals(boatRegistrationNumber) & berth.getBoat().getOwner().getLogin().compareTo(member.getLogin())) {
                    boat = berth.getBoat();
                    return boat;
                }
            }
        }
        throw new BoatNotFoundException("");
    }

    public void removeBoat(Boat boat, User owner) {
        for (Berth berth : berths) {
            if (berth.getBoat() != null) {
                if (berth.getBoat().getRegNumber().equals(boat.getRegNumber())) {
                    berth.removeBoat();
                }
            }
        }
        for (User regUser : regUsers) {
            if (regUser.getLogin().compareTo(owner.getLogin()))
                regUser.removeBoat(boat);
        }

    }
}

