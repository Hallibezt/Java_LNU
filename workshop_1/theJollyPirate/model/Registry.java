package model;

import model.boats.Boat;
import model.roles.Member;
import model.roles.Secretary;
import model.roles.Users;
import view.Mainview;

import java.util.ArrayList;
import java.util.Arrays;

public class Registry {
    private ArrayList<Users> regUsers = new ArrayList<>(); //Everyone is registered with password but only secretary can get the full login options, members registering is for them to handle event booking in the future
    private Berths[] berths = new Berths[200];


    public Registry() {
        hardcoded();
    }

    //initialize with some hardcoded users
    public void hardcoded(){
        for(int i = 0; i < 200; i++){
            berths[i] = new Berths();
            berths[i].setLocation(i+1);
        }
        Users halli = new Secretary("Haraldur", "Blondal", "198410241353", "jonas");
        Users berglind = new Member("Berglind", "Blondal", "198304198800", "sykur");
        Users kristjan = new Member("Kristjan", "Blondal", "201404192380", "nammi");
        regUsers.add(halli);
        regUsers.add(berglind);
        regUsers.add(kristjan);
        System.out.println(regUsers.get(0).getLogin().getUserID());
        System.out.println(regUsers.get(1).getLogin().getUserID());
        System.out.println(regUsers.get(2).getLogin().getUserID());
    }

    // TODO: 2020-08-28 Maybe under UserInterfaceController
    public Users confirmLogin (Login credentials) {
        Login currentLogin = credentials;
        Users loggedIn = null;
        for (int i = 0; i < regUsers.size(); i++) {
            if (regUsers.get(i).getLogin().compareTo(credentials) == true){
                loggedIn = regUsers.get(i);
                }
        }
        return loggedIn;
    }

    // TODO: 2020-08-30 make optional to registerMember with log in or just as member if all registered we need access control for all member objects
    // TODO: 2020-09-03 see if the else logic is working
    public String addMember(Users member) {
        boolean sameID = true;
        String memberUserName = member.getLogin().getUserID();
        while(sameID == true){
            for(int i = 0; i<regUsers.size(); i++){
                if(member.getSocialNumber().equals(regUsers.get(i).getSocialNumber()))
                    throw new IllegalArgumentException();
                if(member.getLogin().getUserID().equals(regUsers.get(i).getLogin().getUserID())){
                    member.getLogin().changeUserID();
                    break;
                }
                else{
                    sameID = false;}
            }
        }

        regUsers.add(member);
        return memberUserName;
    }

    // TODO: 2020-08-30 Remove member also from registered users eda skippa member listanum og searca eftir classtype
    public void removeMember(Users member) {
            for(int i =0; i<regUsers.size();i++){
                if(regUsers.get(i).getLogin().compareTo(member.getLogin())){
                    regUsers.remove(i);
                    for(int j = 0; j <berths.length; j ++){
                        if(berths[j].getBoat() != null){
                             if(berths[j].getCurrentUser().getLogin().compareTo(member.getLogin())){
                                 berths[j].removeBoat();}
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

    public Users[] returnMembers(){
        ArrayList<Users> temp = new ArrayList<>();
        for(int i = 0; i<regUsers.size(); i++) {
            if (regUsers.get(i).getUserType().equalsIgnoreCase("Member")){
                temp.add(regUsers.get(i));
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
        for(int i = 0; i< regUsers.size(); i++){
            if(regUsers.get(i).getLogin().compareTo(login) == true){
                member = regUsers.get(i);
            }
        }
        return member;
    }

    public boolean availableBerth(){
        boolean b = false;//2
        Berths[] list = returnBerths();
        for(int i = 0; i < list.length; i++){
            if(list[i].getCurrentUser() == null){
                b = true;
            }
        }
        return b;
    }

    public Berths[] returnBerths(){
         Berths[] listOfBerths = berths;
         return listOfBerths;
    }

    public boolean checkRegNumber(String regNumber) {
        for(int i = 0; i< berths.length;i++){
            if(berths[i].getBoat() != null){
            if(berths[i].getBoat().getRegNumber().equals(regNumber))
                return true;}
        }
        return false;
    }

    public void updateBerths(int location, Boat boat){
        berths[location-1].addBoat(boat);
    }

    // TODO: 2020-09-02 Maybe add +- search if member not in previous list
    public Berths findBert(Users member){
        Berths[] list = returnBerths();
        Berths berth = list[0];
        for(int i = 0; i < list.length; i++){
            if(list[i] == null){
              if(list[i].hasRentedBert(member) ==true){
                  berth = list[i];
                  return berth;
              }
              else
                  berth = list[i];
            }
        }
        return berth;
    }
}

