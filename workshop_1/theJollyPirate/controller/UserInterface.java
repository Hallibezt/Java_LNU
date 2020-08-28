package controller;

import model.Login;
import model.Registry;
import model.roles.Users;
import view.Mainview;

public class UserInterface {
    private Registry jollyPirate;
    private Mainview view;
    private Users loggedInUser;


    public UserInterface(Registry jollyPirate, Mainview view){
        this.jollyPirate = jollyPirate;
        this.view = view;
    }

    public void welcome(){
        view.welcome();
        if(view.getInput().equalsIgnoreCase("1")){
            loggedInUser = jollyPirate.confirmLogin(view.getCredentials());
            if(loggedInUser == null){
                view.loginFailure();
                view.welcome();}
            view.loggedInMessage(loggedInUser.getFullName());
        }
        else
            view.nonLoginOptions();

    }





}
