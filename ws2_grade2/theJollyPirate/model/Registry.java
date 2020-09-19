package model;

import controller.exceptions_errors.BoatNotFoundException;
import model.boats.Boat;
import model.roles.Secretary;
import model.roles.Users;

import java.io.Serializable;
import java.util.ArrayList;


public class Registry implements Serializable {
    private ArrayList<Users> regUsers = new ArrayList<>(); //Everyone is registered with password but only secretary can get the full login options, members registering is for them to handle event booking in the future
    private final Berths[] berths = new Berths[200];
    private static final long serialVersionUID = -7013385061015921422L;




    public Registry() {
        hardcodedFirstTimeUse();
    }

    //When the program was started the first time at Jolly Pirate the berths got address/location and the secretary was registered
    private void hardcodedFirstTimeUse(){
        Users theSecretary = new Secretary("Haraldur", "Kristjansson", "198410249999", "IceHot1");
        regUsers.add(theSecretary);
        for(int i = 0; i < 200; i++){
            berths[i] = new Berths();
            berths[i].setLocation(i+1);
        }
    }


    // TODO: 2020-09-03 see if the else logic is working
    public String addMember(Users member) {
        boolean sameID = true;
        String memberUserName = member.getLogin().getUserID();
        while(sameID){
            for (Users regUser : regUsers) {
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


    public void removeMember(Users member) { //Removes a member and frees all berths that has member's boats parked.
            for(int i =0; i<regUsers.size();i++){
                if(regUsers.get(i).getLogin().compareTo(member.getLogin())){
                    regUsers.remove(i);
                    for (Berths berth : berths) {
                        if (berth.getBoat() != null) {
                            if (berth.getCurrentUser().getLogin().compareTo(member.getLogin())) {
                                berth.removeBoat();
                            }
                        }
                    }
                }
            }
    }

    public void updateMember(Users member){
        ArrayList<Users> temp = regUsers;
        for(int i = 0; i< temp.size(); i++){
            if(temp.get(i).getLogin().getUserID().equalsIgnoreCase(member.getLogin().getUserID())){
                temp.set(i, member);
            }
        }
        regUsers = temp;
            }

    public Users[] returnMembers(){ //Return all members - exclude secretary or treasury
        ArrayList<Users> temp = new ArrayList<>();
        for (Users regUser : regUsers) {
            if (regUser.getUserType().equalsIgnoreCase("Member")) {
                temp.add(regUser);
            }
        }
        Users[] members = new Users[temp.size()];
            for(int i = 0; i<temp.size(); i++){
                members[i] = temp.get(i);
            }

        return members;
    }

    public Users returnOneMember(Login login){
        Users member = null;
        for (Users regUser : regUsers) {
            if (regUser.getLogin().compareTo(login)) {
                member = regUser;
            }
        }
        return member;
    }

    public boolean availableBerth(){ //See if there are any available berths
        boolean b = false;//2
        Berths[] list = returnBerths();
        for (Berths value : list) {
            if (value.getCurrentUser() == null) {
                b = true;
                break;
            }
        }
        return b;
    }

    public Berths[] returnBerths(){
        return berths;
    }

    public boolean checkRegNumber(String regNumber) { //See if given registration number is in the database
        for (Berths berth : berths) {
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


    public Berths findBert(Users member){
        Berths[] list = returnBerths();
        Berths berth = list[0];
        for (Berths value : list) {
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

    public void updateBoat(Boat boat, Price price ){    //changeOwner() update berth and ownerList changeType() - update berth, owner and possible fee changeLengt() update berth, owner and possible fee
         for(int i = 0; i< berths.length; i++){
            if(berths[i].getLocation() == boat.getLoacation())
                    berths[i].addBoat(boat);
                }
         for(int i = 0; i< regUsers.size(); i++){
             if(regUsers.get(i).getLogin().compareTo(boat.getOwner().getLogin()))
                 regUsers.get(i).updateBoat(boat, price);
         }

    }


    public Boat findBoat(String boatRegistrationNumber, Users member) throws BoatNotFoundException {
        Boat boat;
        for (Berths berth : berths) {
            if (berth.getBoat() != null) {
                if (berth.getBoat().getRegNumber().equals(boatRegistrationNumber) & berth.getBoat().getOwner().getLogin().compareTo(member.getLogin())) {
                    boat = berth.getBoat();
                    return boat;
                }
            }
        }
        throw new BoatNotFoundException("");
    }

    public void removeBoat(Boat boat, Users owner) {
        for (Berths berth : berths) {
            if (berth.getBoat() != null) {
                if (berth.getBoat().getRegNumber().equals(boat.getRegNumber())) {
                    berth.removeBoat();
                }
            }
        }
        for (Users regUser : regUsers) {
            if (regUser.getLogin().compareTo(owner.getLogin()))
                regUser.removeBoat(boat);
        }

    }
}

