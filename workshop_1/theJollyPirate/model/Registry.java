package model;

import model.roles.Member;
import model.roles.Users;
import view.Mainview;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.Arrays;

public class Registry {
    private  ArrayList<Users> regUsers = new ArrayList<>(); //Everyone is registered with password but only secretary can get the full login options, members registering is for them to handle event booking in the future
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
        Users halli = new Member("Haraldur", "Blondal", "198410241353", "jonas");
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
    public void removeMember(String memberID, Mainview view) {
            for(int i =0; i<regUsers.size();i++){
                if(regUsers.get(i).getLogin().getUserID().equals(memberID)){
                    view.confirmRemoveMember( regUsers.get(i));
                    if(view.confirm() == true){
                        regUsers.remove(i);
                        for(int j = 0; i <berths.length; j ++){
                            if(berths[i].getCurrentUser().getLogin().getUserID().equalsIgnoreCase(memberID)){
                                berths[i].removeBoat();
                            }                        }
                        view.memberRemoved();
                    }                }
                else
                    view.memberNotFound();
             }
    }

    public void updateMember(Users member){
        Users[] temp = (Users[]) regUsers.toArray();
        for(int i = 0; i< temp.length; i++){
            if(temp[i].getLogin().getUserID().equalsIgnoreCase(member.getLogin().getUserID())== true){
                temp[i] = member;
            }
        }
        regUsers = (ArrayList<Users>) Arrays.asList(temp);
            }

    public Users[] returnMembers(){
        ArrayList<Users> membersOnly = null;
        if(regUsers.isEmpty())
            throw new NullPointerException();
        for(int i = 0; i<regUsers.size(); i++) {
            if (regUsers.get(i).getClass().equals("class model.roles.Member")){
                membersOnly.add(regUsers.get(i));
            }
        }
        return (Users[]) membersOnly.toArray();
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
            if(list[i] == null){
                b = true;
            }
        }
        return b;
    }

    public Berths[] returnBerths(){
         Berths[] listOfBerths = berths;
         return listOfBerths;
    }

}

