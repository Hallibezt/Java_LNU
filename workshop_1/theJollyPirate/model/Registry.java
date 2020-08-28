package model;

import model.roles.Member;
import model.roles.Users;

import java.util.ArrayList;

public class Registry {
    private  ArrayList<Users> regUsers = new ArrayList<>();


    public Registry() {
        hardcoded();
    }

    //initialize with some hardcoded users
    public void hardcoded(){
        Users halli = new Member("Haraldur", "Blondal", "8410241353", "jonas");
        Users berglind = new Member("Berglind", "Blondal", "8304198800", "sykur");
        Users kristjan = new Member("Kristjan", "Blondal", "1404192380", "nammi");
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
}
