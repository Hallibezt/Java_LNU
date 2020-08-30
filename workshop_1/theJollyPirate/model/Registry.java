package model;

import model.roles.Member;
import model.roles.Users;
import view.Mainview;

import javax.swing.text.View;
import java.util.ArrayList;

public class Registry {
    private  ArrayList<Users> regUsers = new ArrayList<>(); //Everyone is registered with password but only secretary can get the full login options, members registering is for them to handle event booking in the future
    private  ArrayList<Users> members = new ArrayList<>();


    public Registry() {
        hardcoded();
    }

    //initialize with some hardcoded users
    public void hardcoded(){
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
        if(member.getClass().equals("class model.roles.Member"));{
            this.members.add(member);}
        regUsers.add(member);
        return memberUserName;
    }

    // TODO: 2020-08-30 Remove member also from registered users eda skippa member listanum og searca eftir classtype
    public void removeMember(String memberID, Mainview view) {
            for(int i =0; i<members.size();i++){
                if(members.get(i).getLogin().getUserID().equals(memberID)){
                    view.confirmRemoveMember( members.get(i));
                    if(view.confirm() == true){
                        members.remove(i);
                        view.memberRemoved();
                    }
                }
                else
                    view.memberNotFound();
             }
    }

    public Users[] returnMembers(){
        if(members.isEmpty())
            throw new NullPointerException();
        return (Users[]) this.members.toArray();}
}
